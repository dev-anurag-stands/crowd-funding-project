package com.vena_project.crowd_funding.dto.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Email(message = "provide a valid email id")
    private String email;

    @NotBlank(message = "provide a valid password")
    private String password;
}
