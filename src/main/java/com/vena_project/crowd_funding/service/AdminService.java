package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.ResponseDTO.ContributionResponseDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;

import java.util.List;

public interface AdminService {
    String upgradeUserToAdmin(Long userId);
    List<UserResponseDTO> getUsersByRole(String role);
    List<UserResponseDTO>getAllUsers();
    Project updateProjectStatus(Long projectId, ProjectStatus status);
    List<ContributionResponseDTO> getAllContributions();
}
