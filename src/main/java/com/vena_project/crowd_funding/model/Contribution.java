package com.vena_project.crowd_funding.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contribution
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to User table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contributor_id", nullable = false)
    @NotNull(message = "Contributor must not be null")
    private User contributor;

    // Foreign key to Project table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @NotNull(message = "Project must not be null")
    private Project project;

    @NotNull(message = "Amount must not be null")
    @Column(nullable = false)
    private Double amountGiven;

    @NotNull(message = "Date must not be null")
    @Column(nullable = false)
    private LocalDate date;
}


