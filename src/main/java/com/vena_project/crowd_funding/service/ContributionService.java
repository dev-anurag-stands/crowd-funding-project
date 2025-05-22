package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.RequestDTO.ContributionRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.ContributionResponseDTO;
import com.vena_project.crowd_funding.model.Contribution;

import java.util.List;

public interface ContributionService {

    ContributionResponseDTO makeContribution(Long projectId, ContributionRequestDTO dto);

    List<ContributionResponseDTO> getUserContributions(Long userId);

    List<ContributionResponseDTO> getContributionsOnUserProject(Long projectId, Long requesterId);

    List<Contribution> getAllContributions();
}





