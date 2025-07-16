package org.jobtracker.app.UserService.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jobtracker.app.UserService.dto.UserDTO;
import org.jobtracker.app.UserService.filter.AuthFilter;
import org.jobtracker.app.UserService.filter.JwtFilter;
import org.jobtracker.app.UserService.model.UserModel;
import org.jobtracker.app.UserService.repository.UserRepository;
import org.jobtracker.app.UserService.response.AuthResponse;
import org.jobtracker.app.UserService.response.ErrorResponse;
import org.jobtracker.app.UserService.service.JwtService;
import org.jobtracker.app.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class AppConfiguration {

    public static String[] publicUrls = {
        "/user/api/create",
            "/auth/user/login"
    };

    @Autowired
    private JwtService jwtService;

    static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthFilter authFilter(AuthenticationManager authenticationManager) {
        AuthFilter authFilter = new AuthFilter();
        authFilter.setFilterProcessesUrl("/auth/user/login");

        authFilter.setAuthenticationManager(authenticationManager);
        authFilter.setAuthenticationSuccessHandler(((request, response, authentication) -> {

            String username = ((UserPrincipal)authentication.getPrincipal()).getUsername();
            UserDTO userDTO =
                    userRepository.findByUsername(username)
                            .map(data -> UserDTO.builder()
                                    .username(data.getUsername())
                                    .email(data.getEmail())
                                    .password(data.getPassword())
                                    .build()).orElse(new UserDTO());
            String jwtToken = jwtService.generateToken(userDTO);

            AuthResponse authResponse = AuthResponse.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .jwtToken(jwtToken)
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(authResponse));
        }));

        authFilter.setAuthenticationFailureHandler(((request, response, exception) -> {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorMessage(exception.getMessage());

            log.error("An error occurred!!!: {}",exception.getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }));

        return authFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        httpSecurity.cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.requestMatchers(publicUrls)
                        .permitAll().anyRequest().authenticated())
                .addFilterAt(authFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtFilter,UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
