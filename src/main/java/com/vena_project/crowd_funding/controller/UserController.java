package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.service.UserService;
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

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers(){
        return new ResponseEntity<>(userService.usersList(), HttpStatus.OK);
    }

}
