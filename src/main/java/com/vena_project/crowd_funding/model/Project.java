package com.vena_project.crowd_funding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vena_project.crowd_funding.model.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    private String title;

    private String description;

    private double totalAmountAsked;

    private double amountTillNow;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus = ProjectStatus.PENDING;

    private boolean profitable;

    private LocalDate createdOn;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Contribution> contributions;
}