package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.model.Project;
import java.util.List;

public interface ProjectService {
    Project createProject(Long userId, Project project);
    List<Project> getProjectByUserId(Long createdBy);
    List<Project> getApprovedProjects();
    Project getProjectById(Long id);
}
