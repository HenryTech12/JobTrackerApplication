package org.jobtracker.app.UserService.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jobtracker.app.UserService.dto.UserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDTO userDTO = objectMapper.readValue(request.getInputStream(),UserDTO.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            setDetails(request,authenticationToken);

            return this.getAuthenticationManager().authenticate(authenticationToken);
        }
        catch(IOException exception) {
            log.error("An Error Occurred!!!: {} ",exception.getMessage());
        }
        return null;
    }
}
