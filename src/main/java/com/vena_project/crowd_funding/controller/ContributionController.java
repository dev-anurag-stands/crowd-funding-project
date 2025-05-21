package com.vena_project.crowd_funding.controller;

import com.vena_project.crowd_funding.dto.ContributionResponseDTO;
import com.vena_project.crowd_funding.service.ContributionService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contribute")
@Validated
public class ContributionController {

    private final ContributionService contributionService;

    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @PostMapping("/invest/{userId}/{projectId}")
    public ResponseEntity<ContributionResponseDTO> invest(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @RequestParam @NotNull(message = "Amount must not be null")
            @Min(value = 1, message = "Amount must be at least ₹1 or a positive value") Double amountGiven) {

        ContributionResponseDTO responseDTO = contributionService.addInvestmentContribution(userId, projectId, amountGiven);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/donate/{userId}/{projectId}")
    public ResponseEntity<ContributionResponseDTO> donate(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @RequestParam @NotNull(message = "Amount must not be null")
            @Min(value = 1, message = "Amount must be at least ₹1 or a positive value") Double amountGiven) {

        ContributionResponseDTO responseDTO = contributionService.addDonationContribution(userId, projectId, amountGiven);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}



