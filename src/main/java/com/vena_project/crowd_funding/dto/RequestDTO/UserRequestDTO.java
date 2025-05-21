package com.vena_project.crowd_funding.dto.RequestDTO;

import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotNull(message = "id cannot be null")
    private Long id;

    @Email(message = "provide a valid email address")
    private String email;

    @NotBlank(message = "provide a valid password")
    private String password;

    @NotBlank
    private String name;

    @NotNull(message = "role cannot be null")
    private UserRole role;

    public void convertToDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();

    }
}
