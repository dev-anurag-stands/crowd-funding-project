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
        System.out.println(project);
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
    @GetMapping("/non-profitable")
    public ResponseEntity<List<Project>> getNonProfitableProjects() {
        return ResponseEntity.ok(projectService.getProjectsByProfitability(false));
    }
    @GetMapping("/profitable")
    public ResponseEntity<List<Project>> getProfitableProjects() {
        return ResponseEntity.ok(projectService.getProjectsByProfitability(true));
    }
//    @PostMapping("/update/{projectId}")
//    public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @Valid @RequestBody Project updatedProject) {
//        return ResponseEntity.ok(projectService.updateProject(projectId, updatedProject));
//    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted successfully");
    }
}