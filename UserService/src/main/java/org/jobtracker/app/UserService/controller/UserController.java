package org.jobtracker.app.UserService.controller;

import org.jobtracker.app.UserService.dto.UserDTO;
import org.jobtracker.app.UserService.response.UserResponse;
import org.jobtracker.app.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/user/api")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDTO userDTO) {
        UserResponse userResponse = userService.createUser(userDTO);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/get/id/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @GetMapping("/token/validate")
    public boolean validateToken(@RequestBody String token) {
        return userService.validateToken(token);
    }

    @DeleteMapping("/delete/id/{userId}")
    public ResponseEntity<UserResponse> removeUserByID(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.deleteUserWithID(userId),HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updatedUserWithId(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updatedUserById(userId,userDTO),HttpStatus.OK);
    }

    @GetMapping("/get/username/{username}")
    public ResponseEntity<UserDTO> getUserWithUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserWithUsername(username),HttpStatus.OK);
    }

}
