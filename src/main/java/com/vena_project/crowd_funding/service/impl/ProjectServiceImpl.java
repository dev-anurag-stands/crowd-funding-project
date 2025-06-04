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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    ProjectServiceImpl( ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public ProjectResponseDTO createProject(Long userId, ProjectRequestDTO project) {
        User user = userService.getUserById(userId);

        logger.info("Creating project for user with ID: {}", userId);
        if(user.getRole() == UserRole.ADMIN){
            logger.warn("Admin with ID {} attempted to create a project", userId);
            throw new CreateAccessException("Admins cannot create projects.");
        }

        Project newProject = new Project();

        newProject.setTitle(project.getTitle());
        newProject.setDescription(project.getDescription());
        newProject.setTotalAmountAsked(project.getTotalAmountAsked());
        newProject.setCreatedBy(user);
        newProject.setProfitable(project.isProfitable());
        newProject.setCreatedOn(LocalDate.now());
        Project savedProject = projectRepository.save(newProject);

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setProjectId(savedProject.getProjectId());
        projectResponseDTO.convertProjectToDTO(savedProject);
        return projectResponseDTO;
    }

    @Override
    public ProjectResponseDTO getProjectById(Long projectId) {
        logger.info("Fetching project by ID: {}", projectId);
        Project project = findProjectById(projectId);
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.convertProjectToDTO(project);
        return dto;
    }

    @Override
    public List<ProjectDTO> getProjectsByProfitability(boolean profitable) {
        logger.info("Fetching {} projects", profitable ? "profitable" : "non-profitable");
        List<Project> projectList = projectRepository.findByProfitableAndProjectStatus(profitable, ProjectStatus.APPROVED);

        return projectList.stream().map(project -> {
            ProjectDTO dto = new ProjectDTO();
            dto.convertProjectToDTO(project);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long projectId) {
        logger.info("Attempting to delete project ID: {}", projectId);
        Project project = findProjectById(projectId);

        if (project.getProjectStatus() == ProjectStatus.APPROVED) {
            logger.warn("Attempted to delete approved project ID: {}", projectId);
            throw new IllegalStateException("Only pending and rejected projects can be deleted");
        }
        projectRepository.delete(project);
        logger.info("Deleted project ID: {}", projectId);
    }

    @Override
    public Project findProjectById(Long projectId) {
        logger.debug("Looking for project with ID: {}", projectId);
        return projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    logger.error("Project not found with ID: {}", projectId);
                    return new ResourceNotFoundException("Project not found with ID: " + projectId);
                });
    }

    @Override
    public Project saveProject(Project project) {
        logger.debug("Saving project: {}", project.getTitle());
        return projectRepository.save(project);
    }

    @Override
    public ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO dto) {
        logger.info("Updating project ID: {}", projectId);
        Project project = findProjectById(projectId);

        if (project.getProjectStatus() == ProjectStatus.APPROVED) {
            logger.warn("Attempted to update approved project ID: {}", projectId);
            throw new IllegalStateException("Only pending and rejected projects can be updated");
        }

        if (project.getProjectStatus() == ProjectStatus.REJECTED) {
            logger.info("Resetting rejected project ID: {} to pending", projectId);
            project.setProjectStatus(ProjectStatus.PENDING);
        }

        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setTotalAmountAsked(dto.getTotalAmountAsked());
        project.setProfitable(dto.isProfitable());

        Project savedProject = saveProject(project);
        logger.info("Project updated: {}", savedProject.getProjectId());

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.convertProjectToDTO(savedProject);
        return projectResponseDTO;
    }

    @Override
    public void incrementAmountTillNow(Long projectId, Double amount) {
        logger.info("Incrementing amount for project ID: {} by {}", projectId, amount);
        Project project = findProjectById(projectId);
        double newAmount = project.getAmountTillNow() + amount;
        project.setAmountTillNow(newAmount);
        saveProject(project);
    }

    @Override
    public List<ProjectDTO> getApprovedProjects() {
        logger.info("Fetching all approved projects");
        List<Project> projectList = projectRepository.findByProjectStatus(ProjectStatus.APPROVED);

        return projectList.stream().map(project -> {
            ProjectDTO dto = new ProjectDTO();
            dto.convertProjectToDTO(project);
            return dto;
        }).toList();
    }

    @Override
    public List<ProjectResponseDTO> getProjectsByUserAndStatus(Long userId, ProjectStatus status) {
        logger.info("Fetching projects for user ID: {} with status: {}", userId, status);
        User user = userService.getUserById(userId);
        List<Project> projectList;

        if (user.getRole() == UserRole.ADMIN){
            projectList = (status == null)
                    ? projectRepository.findAll()
                    : projectRepository.findByProjectStatus(status);
        } else {
            projectList = (status == null)
                    ? projectRepository.findByCreatedById(userId)
                    : projectRepository.findByCreatedByIdAndProjectStatus(userId, status);
        }

        if (projectList.isEmpty()) {
            logger.warn("No projects found for user ID: {} with status: {}", userId, status);
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