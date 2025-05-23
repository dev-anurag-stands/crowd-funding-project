package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.RequestDTO.ProjectRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectResponseDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectDTO;
import com.vena_project.crowd_funding.exception.CreateAccessException;
import com.vena_project.crowd_funding.exception.ResourceNotFoundException;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.repository.ProjectRepository;
import com.vena_project.crowd_funding.service.ProjectService;
import com.vena_project.crowd_funding.service.UserService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    ProjectServiceImpl( ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public Project createProject(Long userId, ProjectRequestDTO project) {
        User user = userService.getUserById(userId);

        if(user.getRole() == UserRole.ADMIN){
            throw new CreateAccessException("Admins cannot create projects.");
        }

        Project newProject = new Project();

        newProject.setTitle(project.getTitle());
        newProject.setDescription(project.getDescription());
        newProject.setTotalAmountAsked(project.getTotalAmountAsked());
        newProject.setCreatedBy(user);
        newProject.setProfitable(project.isProfitable());
        newProject.setCreatedOn(LocalDate.now());
        newProject.setAmountTillNow(0.0);

        return saveProject(newProject);
    }

    @Override
    public ProjectResponseDTO getProjectById(Long projectId) {
        Project project = findProjectById(projectId);
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.convertProjectToDTO(project);
        return dto;
    }

    @Override
    public List<ProjectDTO> getProjectsByProfitability(boolean profitable) {
        List<Project> projectList = projectRepository.findByProfitableAndProjectStatus(profitable, ProjectStatus.APPROVED);

        return projectList.stream().map(project -> {
            ProjectDTO dto = new ProjectDTO();
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
        Project project = findProjectById(projectId);

        if (project.getProjectStatus() == ProjectStatus.APPROVED ) {
            throw new IllegalStateException("Only pending and rejected projects can be deleted");
        }
        projectRepository.delete(project);
    }

    @Override
    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO dto) {
        Project project = findProjectById(projectId);
        if(project == null){
            throw new RuntimeException("Project not found with ID: " + projectId);
        }

        if(project.getProjectStatus() == ProjectStatus.APPROVED){
            throw new IllegalStateException("Only pending and rejected projects can be updated");
        }

        if(project.getProjectStatus() == ProjectStatus.REJECTED){
            project.setProjectStatus(ProjectStatus.PENDING);
        }

        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setTotalAmountAsked(dto.getTotalAmountAsked());
        project.setProfitable(dto.isProfitable());
        project.setCreatedOn(LocalDate.now());

        Project savedProject = saveProject(project);
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.convertProjectToDTO(savedProject);

        return projectResponseDTO;
    }

    @Override
    public void incrementAmountTillNow(Long projectId, Double amount) {
        Project project = findProjectById(projectId);
        double newAmount = project.getAmountTillNow() + amount;
        project.setAmountTillNow(newAmount);
        saveProject(project);
    }

    @Override
    public List<ProjectDTO> getApprovedProjects() {
        List<Project> projectList = projectRepository.findByProjectStatus(ProjectStatus.APPROVED);
        return projectList.stream().map(project -> {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setProjectId(project.getProjectId());
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

    public List<ProjectResponseDTO> getProjects(Long userId, ProjectStatus status) {
        User user = userService.getUserById(userId);

        List<Project> projectList;

        if (user.getRole().equals(UserRole.ADMIN)) {
            projectList = (status == null)
                    ? projectRepository.findAll()
                    : projectRepository.findByProjectStatus(status);
        } else {
            projectList = (status == null)
                    ? projectRepository.findProjectsByUserId(userId)
                    : projectRepository.findProjectsByCreatedByIdAndProjectStatus(userId, status);
        }

        if (projectList.isEmpty()) {
            throw new ResourceNotFoundException(
                    status == null
                            ? "No projects found."
                            : "No projects found with status " + status + ".");
        }

        return projectList.stream()
                .map(project -> {
                    ProjectResponseDTO dto = new ProjectResponseDTO();
                    dto.convertProjectToDTO(project);
                    return dto;
                })
                .toList();
    }
}