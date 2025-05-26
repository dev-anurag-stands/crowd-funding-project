package com.vena_project.crowd_funding.dto.ResponseDTO;

import com.vena_project.crowd_funding.model.Project;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectResponseDTO {

    private Long projectId;

    private String title;

    private String description;

    private double totalAmountAsked;

    private double amountTillNow;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    private boolean profitable;

    private LocalDate createdOn;

    private UserDTO createdBy;


    public void convertProjectToDTO(Project project) {
        this.projectId = project.getProjectId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.totalAmountAsked = project.getTotalAmountAsked();
        this.amountTillNow = project.getAmountTillNow();
        this.projectStatus = project.getProjectStatus();
        this.profitable = project.isProfitable();
        this.createdOn = project.getCreatedOn();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(project.getCreatedBy().getId());
        userDTO.setName(project.getCreatedBy().getName());

        this.createdBy = userDTO;
    }
}