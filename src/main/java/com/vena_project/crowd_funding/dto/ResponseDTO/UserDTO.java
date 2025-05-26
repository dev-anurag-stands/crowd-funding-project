package com.vena_project.crowd_funding.dto.ResponseDTO;

import com.vena_project.crowd_funding.model.User;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String name;

    public void convertUserToDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
    }
}
