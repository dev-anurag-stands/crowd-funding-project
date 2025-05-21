package com.vena_project.crowd_funding.repository;

import com.vena_project.crowd_funding.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    // Find all contributions for a specific project
    List<Contribution> findByProjectId(Long projectId);
    // Find all contributions made by a specific user
    List<Contribution> findByContributorId(Long contributorId);
    // Optional: Find all contributions for profitable or non-profitable projects
    List<Contribution> findByProjectIsProfitable(boolean isProfitable);
}
