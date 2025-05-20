package com.vena_project.crowd_funding.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContributionResponseDTO {

    private Long contributionId;       // contribution id

    private String contributorName;     // Name of the user who contributed

    private String projectTitle;        // Title of the project to which contribution is made

    private String projectDescription;  // Description of the project

    private Double amountGiven;         // Amount contributed

    private LocalDate contributionDate; // Date of contribution
}


