package com.vena_project.crowd_funding.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContributionRequestDTO {

    @NotNull(message = "Contribution amount must be provided")
    private Double amountGiven;
}