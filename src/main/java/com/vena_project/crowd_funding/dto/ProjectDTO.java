package com.vena_project.crowd_funding.dto;

import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Total amount asked cannot be null")
    private double totalAmountAsked;

    @NotNull(message = "Amount collected so far cannot be null")
    private double amountTillNow;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus = ProjectStatus.PENDING;

    @Column(nullable = false)
    private boolean profitable;

    @NotNull(message = "Creation date cannot be null")
    private LocalDate createdOn;
}
