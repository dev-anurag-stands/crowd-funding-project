package com.vena_project.crowd_funding.dto;

import com.vena_project.crowd_funding.model.Project;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {

    private Long projectId;

    private String title;

    private String description;

    private double totalAmountAsked;
  
    private double amountTillNow;

    private boolean profitable;

    private LocalDate createdOn;

    public void convertProjectToDTO(Project project) {
        this.projectId = project.getProjectId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.totalAmountAsked = project.getTotalAmountAsked();
        this.amountTillNow = project.getAmountTillNow();
        this.profitable = project.isProfitable();
        this.createdOn = project.getCreatedOn();
    }
}