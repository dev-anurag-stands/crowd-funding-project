package com.vena_project.crowd_funding.repository;

import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCreatedById(Long createdBy);

    List<Project> findByCreatedByIdAndProjectStatus(Long userId, ProjectStatus status);

    List<Project> findByProjectStatus(ProjectStatus projectStatus);
    List<Project> findByProfitableAndProjectStatus(Boolean isProfitable, ProjectStatus projectStatus);

}