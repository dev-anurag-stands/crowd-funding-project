package com.vena_project.crowd_funding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    @JsonIgnore
    private User createdBy;

    private String title;

    private String description;

    private double totalAmountAsked;

    private double amountTillNow;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus = ProjectStatus.PENDING;

    private boolean profitable;

    private LocalDate createdOn;

    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Contribution> contributions;
}