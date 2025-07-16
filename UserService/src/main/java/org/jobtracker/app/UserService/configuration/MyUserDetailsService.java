package org.jobtracker.app.UserService.configuration;

import org.jobtracker.app.UserService.dto.UserDTO;
import org.jobtracker.app.UserService.model.UserModel;
import org.jobtracker.app.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService  {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserModel> optionalUserModel = userRepository.findByUsername(username);
        return new UserPrincipal(optionalUserModel.map(data -> new UserDTO(data.getEmail(),
                data.getUsername(), data.getPassword())).orElse(new UserDTO()));
    }
}
