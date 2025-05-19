package com.vena_project.crowd_funding.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserInfo {

    @Email(message = "provide a valid email address")
    @NotBlank(message = "email can not be null or empty")
    private String email;

    @NotBlank(message = "password can not be null or empty")
    private String password;

    @NotBlank(message = "name can not be null or empty")
    private String name;
}
