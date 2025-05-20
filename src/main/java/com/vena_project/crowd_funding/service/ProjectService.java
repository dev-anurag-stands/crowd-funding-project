package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.ApprovedProjectDTO;
import com.vena_project.crowd_funding.dto.ProjectRequestDTO;
import com.vena_project.crowd_funding.dto.ProjectResponseDTO;
import com.vena_project.crowd_funding.model.Project;
import java.util.List;

public interface ProjectService {
    Project createProject(Long userId, ProjectRequestDTO project);
    List<ProjectResponseDTO> getProjectByUserId(Long createdBy);
    List<ApprovedProjectDTO> getApprovedProjects();
    Project getProjectById(Long id);
    List<Project> getProjectsByProfitability(boolean profitability);
    void deleteProject(Long projectId);
}
