package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.ResponseDTO.ContributionResponseDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.enums.UserRole;

import java.util.List;

public interface AdminService {
    String upgradeUserToAdmin(Long userId);
    List <UserResponseDTO> getUsersByRole(Long requesterId, UserRole role);
    List <UserResponseDTO> getAllUsers(Long requesterId);
    void updateProjectStatus(Long projectId, ProjectStatus status);
    List <ContributionResponseDTO> getAllContributions(Long requesterId);
}
