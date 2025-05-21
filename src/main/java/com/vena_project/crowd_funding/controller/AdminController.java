package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.UserInfoDTO;
import com.vena_project.crowd_funding.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoDTO>> getUsers(@RequestParam (required = false) String role){

        if(role==null ){
            System.out.println("Admin Fetched all users");
            return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
        }
        System.out.println("Admin Fetched user by role");
        return new ResponseEntity<>(adminService.getUsersByRole(role), HttpStatus.OK);
    }

    @PatchMapping("/upgrade/{userId}")
    public ResponseEntity<String> upgradeUserToAdmin(@PathVariable  Long userId) {
        System.out.println("Admin updated a user with id " + userId + " as Admin");
        return new ResponseEntity<>(   adminService.upgradeUserToAdmin(userId), HttpStatus.OK);
    }

}
