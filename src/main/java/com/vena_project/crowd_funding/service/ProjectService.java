package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.RequestDTO.ProjectRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectResponseDTO;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;

import java.util.List;

public interface ProjectService {
    ProjectResponseDTO createProject(Long userId, ProjectRequestDTO project);
    List<ProjectResponseDTO> getProjectsByUserAndStatus(Long userId, ProjectStatus status);
    ProjectResponseDTO getProjectById(Long id);
    List<ProjectDTO> getProjectsByProfitability(boolean profitability);
    void deleteProject(Long projectId);
    Project findProjectById(Long projectId);
    Project saveProject(Project project);
    ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO project);
    void incrementAmountTillNow(Long projectId, Double contributedAmount);
    List<ProjectDTO> getApprovedProjects();
}
