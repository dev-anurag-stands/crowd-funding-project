package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.UpdateUserInfoDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<UserResponseDTO> getUserInfo(
            @PathVariable Long id){
        return new ResponseEntity<>(userService.userInfo(id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> listUsers(){
        return new ResponseEntity<>(userService.usersList(), HttpStatus.OK);
    }

    @PatchMapping("/update-info/{id}")
    public ResponseEntity<String> updateUserInfo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserInfoDTO updatedUserInfo){
        userService.updateUserInformation(id, updatedUserInfo);
        return new ResponseEntity<>("user updated", HttpStatus.OK);
    }
}
