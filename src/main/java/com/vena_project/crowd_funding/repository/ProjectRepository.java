package com.vena_project.crowd_funding.repository;

import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT * FROM project WHERE created_by_id = :createdBy", nativeQuery = true)
    List<Project> findProjectsByUserId(@Param("createdBy") Long createdBy);
    List<Project> findByProjectStatus(ProjectStatus projectStatus);
}