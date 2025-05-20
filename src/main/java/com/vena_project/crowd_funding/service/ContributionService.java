package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.ContributionRequestDTO;
import com.vena_project.crowd_funding.dto.ContributionResponseDTO;

public interface ContributionService {

    ContributionResponseDTO addInvestment(ContributionRequestDTO contributionRequestDTO);

    ContributionResponseDTO addDonation(ContributionRequestDTO contributionRequestDTO);

    // For future use:
    // List<ContributionResponseDTO> getContributionsByProjectOwner(Long ownerId);
}

