package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.LoginRequestDTO;
import com.vena_project.crowd_funding.dto.UpdateUserInfoDTO;
import com.vena_project.crowd_funding.dto.UserDTO;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO user){
        if(user.getRole() == UserRole.ADMIN){
            throw new IllegalArgumentException("Admin cannot be registered, submit a request first.");
        }
        userService.register(user);
        return new ResponseEntity<>("user registered", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest){
        userService.login(loginRequest);
        return new ResponseEntity<>("user logged in", HttpStatus.OK);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<User> getUserInfo(
            @NotNull(message = "user id cannot be null") @PathVariable Long id){
        System.out.println("hello user with id : "+id);
        return new ResponseEntity<>(userService.userInfo(id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers(){
        return new ResponseEntity<>(userService.usersList(), HttpStatus.OK);
    }


    @PatchMapping("/update-info/{id}")
    public ResponseEntity<String> updateUserInfo(
            @NotNull(message = "User ID cannot be null") @PathVariable Long id,
            @Valid @RequestBody UpdateUserInfoDTO updatedUserInfo){
        System.out.println("hello");
        userService.updateUserInformation(updatedUserInfo);
        return new ResponseEntity<>("user updated", HttpStatus.OK);
    }
}
