package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.RequestDTO.ProjectRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ProjectResponseDTO;
import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
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
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(
            @PathVariable Long userId,
            @RequestParam(required = false) ProjectStatus status) {
        return new ResponseEntity<>(projectService.getProjects(userId, status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<ProjectDTO>> getProjectsByType(@RequestParam boolean isProfitable) {
        return new ResponseEntity<>(projectService.getProjectsByProfitability(isProfitable), HttpStatus.OK);
    }

    @PostMapping("/update/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectRequestDTO updatedProject) {
        return ResponseEntity.ok(projectService.updateProject(projectId, updatedProject));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>("Project deleted successfully", HttpStatus.OK);
    }
}
