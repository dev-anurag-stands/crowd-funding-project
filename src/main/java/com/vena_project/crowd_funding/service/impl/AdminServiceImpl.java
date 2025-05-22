package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.ResponseDTO.ContributionResponseDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectResponseDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.exception.UserAlreadyAdminException;
import com.vena_project.crowd_funding.model.Contribution;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.AdminService;
import com.vena_project.crowd_funding.service.ContributionService;
import com.vena_project.crowd_funding.service.ProjectService;
import com.vena_project.crowd_funding.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final UserService userService;
    private final ProjectService projectService;
    private final ContributionService contributionService;

    public AdminServiceImpl(ContributionService contributionService, UserService userService, ProjectService projectService) {
        this.contributionService = contributionService;
        this.userService = userService;
        this.projectService = projectService;
    }

        @Override
    public String upgradeUserToAdmin(Long userId) {
        logger.info("Attempting to upgrade user with id {} to ADMIN", userId);

        User user = userService.getUserById(userId);

        if (user.getRole() == UserRole.ADMIN) {
            logger.warn("User with id {} is already ADMIN", userId);
            throw new UserAlreadyAdminException("User with id " + userId + " is already an ADMIN.");
        }

        user.setRole(UserRole.ADMIN);
        userService.saveUser(user);
        logger.info("User with id {} upgraded to ADMIN successfully", userId);
        return "User with id " + userId + " upgraded to ADMIN successfully.";
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        logger.info("Fetching all users");
        List<UserResponseDTO> users = userService.usersList();
        logger.info("Total users fetched: {}", users.size());
        return users;
    }

    @Override
    public List<UserResponseDTO> getUsersByRole(String role) {
        logger.info("Fetching users with role '{}'", role);

        List<UserResponseDTO> usersByRole = userService.usersList().stream()
                .filter(user -> user.getRole().toString().equalsIgnoreCase(role))
                .collect(Collectors.toList());

        logger.info("Found {} users with role '{}'", usersByRole.size(), role);
        return usersByRole;
    }

    @Override
    public Project updateProjectStatus(Long projectId, ProjectStatus status) {
        logger.info("Updating project id {} status to {}", projectId, status);

        Project project = projectService.findProjectById(projectId);

        if (project.getProjectStatus() != ProjectStatus.PENDING) {
            String errMsg = "Project status already set to "
                    + project.getProjectStatus().name().toLowerCase() + " and cannot be changed.";
            logger.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }

        if (project.getProjectStatus() == status) {
            String errMsg = "Project is already " + status.toString().toLowerCase() + ".";
            logger.warn(errMsg);
            throw new IllegalStateException(errMsg);
        }

        project.setProjectStatus(status);
        Project savedProject = projectService.saveProject(project);

        logger.info("Project id {} status updated to {}", projectId, status);
        return savedProject;
    }

    @Override
    public List<ContributionResponseDTO> getAllContributions() {

        List<Contribution> contributions = contributionService.getAllContributions();

        return contributions.stream().map(contribution -> {
            User contributor = userService.getUserById(contribution.getContributor().getId());
            ProjectResponseDTO projectDTO = projectService.getProjectById(contribution.getProject().getProjectId());

            ContributionResponseDTO dto = new ContributionResponseDTO();
            dto.setFromContribution(contribution, contributor, projectDTO);
            return dto;
        }).collect(Collectors.toList());
    }
}