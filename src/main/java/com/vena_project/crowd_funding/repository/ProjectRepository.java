package com.vena_project.crowd_funding.repository;

import com.vena_project.crowd_funding.model.Project;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Optional: custom query methods if needed
}


// change it before push