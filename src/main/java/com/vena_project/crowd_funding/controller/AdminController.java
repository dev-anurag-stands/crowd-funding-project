package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vena_project.crowd_funding.model.enums.UserRole.ADMIN;

@RestController
@RequestMapping
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register-user")
    public ResponseEntity<User> registerAdminUser(@RequestBody User user){
        try {
            User registeredUser = adminService.registerAdminUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users= adminService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
