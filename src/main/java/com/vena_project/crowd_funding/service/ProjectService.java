package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.*;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;

import java.util.List;

public interface ProjectService {
    Project createProject(Long userId, ProjectRequestDTO project);
    List<ProjectResponseDTO> getProjectByUserId(Long createdBy);
    List<ApprovedProjectDTO> getApprovedProjects();
    ProjectDTO getProjectById(Long id);
    List<ProjectTypeDTO> getProjectsByProfitability(boolean profitability);
    void deleteProject(Long projectId);
    Project findProjectById(Long projectId);
    Project saveProject(Project project);
    Project updateProject(Long projectId, ProjectRequestDTO project);
    void incrementAmountTillNow(Long projectId, Double contributedAmount);
}
