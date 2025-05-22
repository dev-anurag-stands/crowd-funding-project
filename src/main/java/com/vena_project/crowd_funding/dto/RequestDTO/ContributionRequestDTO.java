package com.vena_project.crowd_funding.dto.RequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContributionRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Amount given cannot be null")
    @Min(value = 1, message = "Amount must be at least 1")
    private Double amountGiven;
}

