package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.UserInfoDTO;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vena_project.crowd_funding.model.enums.UserRole.ADMIN;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoDTO>> getAllUsers(){
        System.out.println("Admin Fetched All Users");
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @PatchMapping("/upgrade/{userId}")
    public ResponseEntity<String> upgradeUserToAdmin(@PathVariable Long userId) {
        System.out.println("Admin updated a user with id " + userId + " as Admin");
        return new ResponseEntity<>(   adminService.upgradeUserToAdmin(userId), HttpStatus.OK);
    }

}
