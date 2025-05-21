package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.*;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Project> createProject (@PathVariable Long userId, @Valid @RequestBody ProjectRequestDTO project){
        return new ResponseEntity<>(projectService.createProject(userId, project), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(projectService.getProjectByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ApprovedProjectDTO>> getApprovedProjects() {
        return new ResponseEntity<>(projectService.getApprovedProjects(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        ProjectDTO dto = new ProjectDTO();
        dto.convertProjectToDTO(project);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<ProjectTypeDTO>> getNonProfitableProjects(@RequestParam boolean isProfitable) {
        return new ResponseEntity<>(projectService.getProjectsByProfitability(isProfitable), HttpStatus.OK);
    }

    @PostMapping("/update/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectRequestDTO updatedProject) {
        return ResponseEntity.ok(projectService.updateProject(projectId, updatedProject));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>("Project deleted successfully", HttpStatus.OK);
    }
}
