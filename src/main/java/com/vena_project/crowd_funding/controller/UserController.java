package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.LoginRequest;
import com.vena_project.crowd_funding.dto.UpdateUserInfo;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user){
        userService.register(user);
        return new ResponseEntity<>("user registered", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest){
        userService.login(loginRequest);
        return new ResponseEntity<>("user logged in", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers(){
        return new ResponseEntity<>(userService.usersList(), HttpStatus.OK);
    }


    @PatchMapping("/update-info")
    public ResponseEntity<String> updateUserInfo(@Valid @RequestBody UpdateUserInfo updatedUserInfo){
        System.out.println("hello");
        userService.updateUserInformation(updatedUserInfo);
        return new ResponseEntity<>("user updated", HttpStatus.OK);
    }
}
