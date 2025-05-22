package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.RequestDTO.ProjectRequestDTO;
import com.vena_project.crowd_funding.dto.ProjectDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectResponseDTO;
import com.vena_project.crowd_funding.model.Project;

import java.util.List;

public interface ProjectService {
    Project createProject(Long userId, ProjectRequestDTO project);
    List<ProjectResponseDTO> getProjectByUserId(Long createdBy);
    List<ProjectResponseDTO> getProjects(ProjectStatus status);
    ProjectResponseDTO getProjectById(Long id);
    List<ProjectDTO> getProjectsByProfitability(boolean profitability);
    void deleteProject(Long projectId);
    Project findProjectById(Long projectId);
    Project saveProject(Project project);
    Project updateProject(Long projectId, ProjectRequestDTO project);
    void incrementAmountTillNow(Long projectId, Double contributedAmount);
    List<Project> getAllProjects();

}
