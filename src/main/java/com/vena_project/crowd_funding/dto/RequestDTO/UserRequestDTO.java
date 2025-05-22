package com.vena_project.crowd_funding.dto.RequestDTO;

import com.vena_project.crowd_funding.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {
    @Email(message = "provide a valid email address")
    private String email;

    @NotBlank(message = "provide a valid password")
    private String password;

    @NotBlank(message = "name cannot be empty string or null")
    private String name;

    public void convertToDTO(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
