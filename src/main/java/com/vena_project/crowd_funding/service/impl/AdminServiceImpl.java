package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.ProjectDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.exception.ResourceNotFoundException;
import com.vena_project.crowd_funding.exception.UserAlreadyAdminException;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.AdminService;
import com.vena_project.crowd_funding.service.ProjectService;
import com.vena_project.crowd_funding.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final ProjectService projectService;

    public AdminServiceImpl(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    public String upgradeUserToAdmin(Long userId) {
        User user = userService.getUserById(userId);

        if (user.getRole() == UserRole.ADMIN) {
            throw new UserAlreadyAdminException("User with id " + userId + " is already an ADMIN.");
        }

        user.setRole(UserRole.ADMIN);
        userService.saveUser(user);
        return "User with id " + userId + " upgraded to ADMIN successfully.";
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userService.usersList();
    }

    @Override
    public List<UserResponseDTO> getUsersByRole(String role) {

        return userService.usersList().stream()
                .filter(user -> {
                    return user.getRole().toString().equalsIgnoreCase(role);
                })
                .collect(Collectors.toList());
    }

    public Project updateProjectStatus(Long projectId, ProjectStatus status) {

        Project project = projectService.findProjectById(projectId);

        if (project.getProjectStatus() != ProjectStatus.PENDING) {
            throw new IllegalStateException("Project status has already been set to "
                    + project.getProjectStatus().name().toLowerCase() + " and cannot be changed again.");
        }
        if (project.getProjectStatus() == status) {
            throw new IllegalStateException("Project is already " + status.toString().toLowerCase() + ".");
        }
        project.setProjectStatus(status);
        return projectService.saveProject(project);
    }

}