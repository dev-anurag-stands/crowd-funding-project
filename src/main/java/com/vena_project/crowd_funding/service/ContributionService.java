package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.ContributionResponseDTO;

public interface ContributionService {
    ContributionResponseDTO addInvestmentContribution(Long userId, Long projectId, Double amountGiven);
    ContributionResponseDTO addDonationContribution(Long userId, Long projectId, Double amountGiven);
}




