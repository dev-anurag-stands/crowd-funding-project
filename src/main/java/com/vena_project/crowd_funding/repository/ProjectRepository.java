package com.vena_project.crowd_funding.repository;

import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.amountTillNow = p.amountTillNow + :amountToAdd WHERE p.projectId = :projectId")
    int incrementAmountTillNow(Long projectId, Double amountToAdd);

    @Query(value = "SELECT * FROM project WHERE created_by_id = :createdBy", nativeQuery = true)
    List<Project> findProjectsByUserId(@Param("createdBy") Long createdBy);

    @Query("SELECT p FROM Project p WHERE p.createdBy.id = :userId AND p.projectStatus = :status")
    List<Project> findProjectsByCreatedByIdAndProjectStatus(@Param("userId") Long userId, @Param("status") ProjectStatus status);

    List<Project> findByProjectStatus(ProjectStatus projectStatus);
    List<Project> findByProfitableAndProjectStatus(Boolean isProfitable, ProjectStatus projectStatus);

}