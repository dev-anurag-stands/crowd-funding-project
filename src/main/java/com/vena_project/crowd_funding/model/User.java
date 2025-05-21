package com.vena_project.crowd_funding.model;

import com.vena_project.crowd_funding.dto.RequestDTO.UserRequestDTO;
import com.vena_project.crowd_funding.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @OneToMany(mappedBy = "createdBy")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "contributorId")
    private List<Contribution> contributions = new ArrayList<>();

    public void updateFromRequestDTO(UserRequestDTO userDTO){
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
    }
}
