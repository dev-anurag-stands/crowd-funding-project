package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import java.util.List;

public interface AdminService {
    String upgradeUserToAdmin(Long userId);
    List<UserResponseDTO> getUsersByRole(String role);
    List<UserResponseDTO>getAllUsers();
}
