package com.vena_project.crowd_funding.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.vena_project.crowd_funding.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "provide a valid name")
    private String name;

    @NotBlank(message = "provide a valid email address")
    @Email
    private String email;

    @NotBlank(message = "provide a valid password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
