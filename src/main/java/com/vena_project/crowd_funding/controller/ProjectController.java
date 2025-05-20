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

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Project> createProject (@PathVariable Long userId, @Valid @RequestBody ProjectRequestDTO project){
        System.out.println(project);
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
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }
    @GetMapping("/non-profitable")
    public ResponseEntity<List<ProjectTypeDTO>> getNonProfitableProjects() {
        return new ResponseEntity<>(projectService.getProjectsByProfitability(false), HttpStatus.OK);
    }
    @GetMapping("/profitable")
    public ResponseEntity<List<ProjectTypeDTO>> getProfitableProjects() {
        return new ResponseEntity<>(projectService.getProjectsByProfitability(true), HttpStatus.OK);
    }
//    @PostMapping("/update/{projectId}")
//    public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @Valid @RequestBody Project updatedProject) {
//        return ResponseEntity.ok(projectService.updateProject(projectId, updatedProject));
//    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>("Project deleted successfully", HttpStatus.OK);
    }
}