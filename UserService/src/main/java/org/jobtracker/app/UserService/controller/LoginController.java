package org.jobtracker.app.UserService.controller;

import org.jobtracker.app.UserService.dto.UserDTO;
import org.jobtracker.app.UserService.response.AuthResponse;
import org.jobtracker.app.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        return "authenticating...";
    }
}
