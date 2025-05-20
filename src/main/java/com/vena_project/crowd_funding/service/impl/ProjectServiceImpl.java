package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.*;
import com.vena_project.crowd_funding.exception.ResourceNotFoundException;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.repository.ProjectRepository;
import com.vena_project.crowd_funding.repository.UserRepository;
import com.vena_project.crowd_funding.service.ProjectService;
import com.vena_project.crowd_funding.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private UserService userService;

    ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(Long userId, ProjectRequestDTO project) {
        User user = userService.userInfo(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Project newProject = new Project();

        newProject.setTitle(project.getTitle());
        newProject.setDescription(project.getDescription());
        newProject.setTotalAmountAsked(project.getTotalAmountAsked());
        newProject.setCreatedBy(user.getId());
        newProject.setProfitable(project.isProfitable());
        newProject.setCreatedOn(LocalDate.now());
        newProject.setAmountTillNow(0.0);

        return projectRepository.save(newProject);
    }

    @Override
    public List<ProjectResponseDTO> getProjectByUserId(Long userId) {
        List<Project> projectList = projectRepository.findProjectsByUserId(userId);
        return projectList.stream().map(project -> {
            ProjectResponseDTO dto = new ProjectResponseDTO();
            dto.setTitle(project.getTitle());
            dto.setDescription(project.getDescription());
            dto.setTotalAmountAsked(project.getTotalAmountAsked());
            dto.setAmountTillNow(project.getAmountTillNow());
            dto.setProjectStatus(project.getProjectStatus());
            dto.setProfitable(project.isProfitable());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ApprovedProjectDTO> getApprovedProjects() {
        List<Project> projectList = projectRepository.findByProjectStatus(ProjectStatus.APPROVED);
        return projectList.stream().map(project -> {
                    ApprovedProjectDTO dto = new ApprovedProjectDTO();
                    dto.setTitle(project.getTitle());
                    dto.setDescription(project.getDescription());
                    dto.setTotalAmountAsked(project.getTotalAmountAsked());
                    dto.setAmountTillNow(project.getAmountTillNow());
                    dto.setProfitable(project.isProfitable());
                    dto.setCreatedOn(project.getCreatedOn());
                    return dto;
                }
        ).toList();
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
        ProjectDTO dto = new ProjectDTO();

        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setTotalAmountAsked(project.getTotalAmountAsked());
        dto.setAmountTillNow(project.getAmountTillNow());
        dto.setCreatedOn(project.getCreatedOn());
        dto.setProfitable(project.isProfitable());
        dto.setProjectStatus(project.getProjectStatus());
        return dto;
    }

    @Override
    public List<ProjectTypeDTO> getProjectsByProfitability(boolean profitable) {
        List<Project> projectList = projectRepository.findByProfitable(profitable);

        return projectList.stream().map(project -> {
            ProjectTypeDTO dto = new ProjectTypeDTO();
            dto.setTitle(project.getTitle());
            dto.setDescription(project.getDescription());
            dto.setTotalAmountAsked(project.getTotalAmountAsked());
            dto.setAmountTillNow(project.getAmountTillNow());
            dto.setCreatedOn(project.getCreatedOn());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        if (project.getProjectStatus() == ProjectStatus.APPROVED ) {
            throw new IllegalStateException("Only pending and rejected projects can be deleted");
        }
        projectRepository.delete(project);
    }
}