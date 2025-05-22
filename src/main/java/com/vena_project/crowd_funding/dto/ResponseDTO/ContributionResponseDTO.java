package com.vena_project.crowd_funding.dto.ResponseDTO;

import com.vena_project.crowd_funding.model.Contribution;
import com.vena_project.crowd_funding.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContributionResponseDTO {

    @NotNull(message = "Contribution ID cannot be null")
    private Long contributionId;

    @NotBlank(message = "Contributor name cannot be blank")
    private String contributorName;

    @NotBlank(message = "Project title cannot be blank")
    private String projectTitle;

    @NotBlank(message = "Project description cannot be blank")
    private String projectDescription;

    @NotNull(message = "Amount given cannot be null")
    private Double amountGiven;

    @NotNull(message = "Contribution date cannot be null")
    private LocalDate contributionDate;

    public void setFromContribution(Contribution contribution, User contributor, ProjectResponseDTO project) {
        this.contributionId = contribution.getId();
        this.contributorName = contributor.getName();
        this.projectTitle = project.getTitle();
        this.projectDescription = project.getDescription();
        this.amountGiven = contribution.getAmountGiven();
        this.contributionDate = contribution.getDate();
    }
}




