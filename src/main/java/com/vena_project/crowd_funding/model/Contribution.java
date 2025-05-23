package com.vena_project.crowd_funding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @JoinColumn(name = "contributorId", nullable = false)
    private User contributor;

    // Foreign key to Project table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId", nullable = false)
    @JsonIgnore
    private Project project;

    @Column(nullable = false)
    private Double amountGiven;

    @Column(nullable = false)
    private LocalDate date;
}


