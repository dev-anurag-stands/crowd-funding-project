package com.vena_project.crowd_funding.repository;

import com.vena_project.crowd_funding.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findByProject_ProjectId(Long projectId);
    List<Contribution> findByContributor_Id(Long userId);

}

