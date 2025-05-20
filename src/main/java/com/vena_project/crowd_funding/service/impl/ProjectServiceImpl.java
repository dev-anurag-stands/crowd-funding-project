package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.exception.ResourceNotFoundException;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.repository.ProjectRepository;
import com.vena_project.crowd_funding.repository.UserRepository;
import com.vena_project.crowd_funding.service.ProjectService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Project createProject(Long userId, Project project) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        project.setCreatedBy(user.getId());
        project.setProfitable(project.isProfitable());
        project.setCreatedOn(LocalDate.now());
        project.setAmountTillNow(0.0);
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectByUserId(Long userId) {
        System.out.println("createdById = " + userId);
        List<Project> list = projectRepository.findProjectsByUserId(userId);
        System.out.println("List is empty" + list);
        return list;
    }

    @Override
    public List<Project> getApprovedProjects() {
        return projectRepository.findByProjectStatus(ProjectStatus.APPROVED);
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