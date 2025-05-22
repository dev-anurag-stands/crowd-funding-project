package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.RequestDTO.LoginRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UserRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO user){
        if(user.getRole() == UserRole.ADMIN){
            throw new IllegalArgumentException("Admin cannot be registered, submit a request first.");
        }
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest){
        userService.login(loginRequest);
        return new ResponseEntity<>("user logged in", HttpStatus.OK);
    }
}
