package com.vena_project.crowd_funding.dto.ResponseDTO;

import com.vena_project.crowd_funding.model.Contribution;
import com.vena_project.crowd_funding.model.User;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ContributionResponseDTO {

    private Long contributionId;
    private String contributorName;
    private String projectTitle;
    private String projectDescription;
    private Double amountGiven;
    private LocalDate contributionDate;

    public void setFromContribution(Contribution contribution, String contributorName, ProjectResponseDTO project) {
        this.contributionId = contribution.getId();
        this.contributorName = contributorName;
        this.projectTitle = project.getTitle();
        this.projectDescription = project.getDescription();
        this.amountGiven = contribution.getAmountGiven();
        this.contributionDate = contribution.getDate();
    }
}




