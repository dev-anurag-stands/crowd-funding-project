package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.ResponseDTO.ContributionResponseDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.enums.UserRole;
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
    public ResponseEntity<List<UserResponseDTO>> getUsers(@RequestParam (required = false) UserRole role){
        if(role==null ){
            return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(adminService.getUsersByRole(role), HttpStatus.OK);
    }

    @PatchMapping("/upgrade/{userId}")
    public ResponseEntity<String> upgradeUserToAdmin(@PathVariable  Long userId) {
        return new ResponseEntity<>(adminService.upgradeUserToAdmin(userId), HttpStatus.OK);
    }

    @PatchMapping("/project/{projectId}")
    public ResponseEntity<String> updateProjectStatus(
            @PathVariable Long projectId,
            @RequestParam ProjectStatus status) {

        adminService.updateProjectStatus(projectId, status);

        return new ResponseEntity<>("Project with ID " + projectId + " has been " + status, HttpStatus.OK);
    }

    @GetMapping("/contributions")
    public ResponseEntity<List<ContributionResponseDTO>> getAllContributions() {
        return new ResponseEntity<>(adminService.getAllContributions(), HttpStatus.OK);
    }
}
