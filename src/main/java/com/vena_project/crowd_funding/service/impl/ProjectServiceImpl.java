package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.ApprovedProjectDTO;
import com.vena_project.crowd_funding.dto.ProjectRequestDTO;
import com.vena_project.crowd_funding.dto.ProjectResponseDTO;
import com.vena_project.crowd_funding.exception.ResourceNotFoundException;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.repository.ProjectRepository;
import com.vena_project.crowd_funding.repository.UserRepository;
import com.vena_project.crowd_funding.service.ProjectService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Project createProject(Long userId, ProjectRequestDTO project) {
        User user = userRepository.findById(userId)
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
                    return dto;
                }
        ).toList();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
    }

    @Override
    public List<Project> getProjectsByProfitability(boolean profitable) {
        return projectRepository.findByProfitable(profitable);
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