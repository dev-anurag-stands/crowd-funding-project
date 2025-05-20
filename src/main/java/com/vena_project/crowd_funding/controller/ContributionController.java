package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.service.ContributionService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vena_project.crowd_funding.dto.ContributionRequestDTO;
import com.vena_project.crowd_funding.dto.ContributionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/contribute")
public class ContributionController {

    private final ContributionService contributionService;

    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @PostMapping("/invest/{userId}/{projectId}")
    public ResponseEntity<ContributionResponseDTO> invest(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @RequestBody ContributionRequestDTO requestDTO) {

        ContributionResponseDTO responseDTO =
                contributionService.addInvestmentContribution(userId, projectId, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/donate/{userId}/{projectId}")
    public ResponseEntity<ContributionResponseDTO> donate(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @RequestBody ContributionRequestDTO requestDTO) {

        ContributionResponseDTO responseDTO =
                contributionService.addDonationContribution(userId, projectId, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}


