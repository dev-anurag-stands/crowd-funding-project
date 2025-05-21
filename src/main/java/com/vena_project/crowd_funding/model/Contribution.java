package com.vena_project.crowd_funding.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to User table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contributor_id", nullable = false)
    @NotNull(message = "ContributorId must not be null")
    private Long contributorId;

    // Foreign key to Project table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @NotNull(message = "Project must not be null")
    private Long projectId;

    @NotNull(message = "Amount must not be null")
    @Column(nullable = false)
    private Double amountGiven;

    @NotNull(message = "Date must not be null")
    @Column(nullable = false)
    private LocalDate date;
}


