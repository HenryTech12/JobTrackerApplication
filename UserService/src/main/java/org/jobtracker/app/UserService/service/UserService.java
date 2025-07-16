package org.jobtracker.app.UserService.service;

import lombok.extern.slf4j.Slf4j;
import org.jobtracker.app.UserService.dto.UserDTO;
import org.jobtracker.app.UserService.model.UserModel;
import org.jobtracker.app.UserService.repository.UserRepository;
import org.jobtracker.app.UserService.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public UserResponse createUser(UserDTO userDTO) {
        UserResponse userResponse = null;
        if(!Objects.isNull(userDTO)) {
            UserModel userModel = new UserModel();
            userModel.setEmail(userDTO.getEmail());
            userModel.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userModel.setUsername(userDTO.getUsername());

            userRepository.save(userModel);
            log.info("user with id: {} saved to db.",userModel.getUserId());
            userResponse = new UserResponse(userModel.getUserId(),userModel.getEmail(),"Account created successfully",true);
        }
        return userResponse;
    }

    public UserDTO getUserById(Long userId) {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        return userModelOptional.map(
                data -> UserDTO.
                        builder()
                        .email(data.getEmail())
                        .username(data.getUsername())
                        .password(data.getPassword())
                        .build()
        ).orElse(new UserDTO());
    }

    public boolean validateToken(String token) {
        return ( !jwtService.isTokenExpired(token));
    }

    public UserResponse deleteUserWithID(Long userId) {
        userRepository.deleteById(userId);
        return new UserResponse(userId,null,"Account Deleted Successfully", false);
    }

    public UserDTO updatedUserById(Long userId,UserDTO userDTO) {
       UserModel userModel =
                userRepository.findById(userId).orElse(null);
        if(!Objects.isNull(userModel)) {
            userModel.setEmail(userDTO.getEmail());
            userModel.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userModel.setUsername(userDTO.getEmail());
            userRepository.save(userModel);

            log.info("user with id: {} details updated",userId);
        }
        return userDTO;
    }

    public UserDTO getUserWithUsername(String username) {
        Optional<UserModel> userModelOptional =
                userRepository.findByUsername(username);
        return userModelOptional.map(data -> UserDTO.builder()
                .email(data.getEmail())
                .username(data.getUsername())
                .password(data.getPassword())
                .build()).orElse(new UserDTO());
    }
}
