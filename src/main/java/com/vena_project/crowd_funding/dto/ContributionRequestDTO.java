package com.vena_project.crowd_funding.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContributionRequestDTO
{

    @NotNull(message = "Project ID is required")
    private Long projectId;   // ID of the approved project being contributed to

    @NotNull(message = "Contributor User ID is required")
    private Long contributorId;   // ID of the user making the contribution

    @NotNull(message = "Contribution amount must be provided")
    private Double amountGiven;   // Amount user is donating/investing
}
