package com.vena_project.crowd_funding.repository;

import com.vena_project.crowd_funding.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    @Query("SELECT c FROM Contribution c WHERE c.project.id = :projectId")
    List<Contribution> findByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT c FROM Contribution c WHERE c.contributor.id = :userId")
    List<Contribution> findByContributorId(@Param("userId") Long userId);

}

