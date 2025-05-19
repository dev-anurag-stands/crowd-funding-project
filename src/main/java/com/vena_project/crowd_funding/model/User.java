package com.vena_project.crowd_funding.model;

import com.vena_project.crowd_funding.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name cannot be empty string or null")
    private String name;

    @Email(message = "provide a valid email address")
    @NotBlank
    private String email;

    @NotBlank(message = "provide a valid password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @OneToMany(mappedBy = "createdBy")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "contributor")
    private List<Contribution> contributions = new ArrayList<>();
}
