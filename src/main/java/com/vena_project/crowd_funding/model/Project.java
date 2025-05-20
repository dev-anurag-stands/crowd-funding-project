package com.vena_project.crowd_funding.model;

import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by_id", nullable = false)
    private Long createdBy;

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
    private boolean isProfitable;

    @NotNull(message = "Creation date cannot be null")
    private LocalDate createdAt;
}