package com.vena_project.crowd_funding.dto;

import com.vena_project.crowd_funding.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserInfoDTO {
    @NotNull(message = "id cannot be null")
    private Long id;

    @Email(message = "provide a valid email address")
    private String email;

    @NotBlank
    private String name;

    @NotNull(message = "role cannot be null")
    private UserRole role;
}
