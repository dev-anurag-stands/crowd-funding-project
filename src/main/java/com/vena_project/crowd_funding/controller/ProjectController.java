package com.vena_project.crowd_funding.controller;

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
    public ResponseEntity<Project> createProject (@PathVariable Long userId, @Valid @RequestBody Project project){
        return new ResponseEntity<>(projectService.createProject(userId, project), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUser(@PathVariable Long userId) {
        System.out.println("createdById = " + userId);
        return ResponseEntity.ok(projectService.getProjectByUserId(userId));
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Project>> getApprovedProjects() {
        return ResponseEntity.ok(projectService.getApprovedProjects());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }
}