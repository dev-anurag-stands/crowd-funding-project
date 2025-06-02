package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.RequestDTO.UpdatePasswordRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UpdateUserInfoRequestDTO;
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
        return new ResponseEntity<>(userService.getUserInfo(id), HttpStatus.OK);
    }


    @PatchMapping("/update-info/{id}")
    public ResponseEntity<UserResponseDTO> updateUserInfo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserInfoRequestDTO updatedUserInfo){
        return new ResponseEntity<>(userService.updateUserInformation(id, updatedUserInfo), HttpStatus.OK);
    }

    @PatchMapping("/update-password/{userId}")
    public ResponseEntity<String> updateUserPassword(@PathVariable Long userId, @Valid @RequestBody UpdatePasswordRequestDTO updatePasswordRequest){
        userService.updatePassword(userId, updatePasswordRequest);
        return new ResponseEntity<>("password updated", HttpStatus.OK);
    }
}
