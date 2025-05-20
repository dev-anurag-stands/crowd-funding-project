package com.vena_project.crowd_funding.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserInfoDTO {

    @Email(message = "provide a valid email address")
    @NotBlank(message = "email can not be null or empty")
    private String email;

    @NotBlank(message = "name can not be null or empty")
    private String name;
}
