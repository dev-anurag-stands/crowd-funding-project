package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.LoginRequest;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user){
        authService.register(user);
        return new ResponseEntity<>("user registered", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest){
        authService.login(loginRequest);
        return new ResponseEntity<>("user logged in", HttpStatus.OK);
    }
}
