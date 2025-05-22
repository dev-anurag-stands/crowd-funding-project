package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.RequestDTO.LoginRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UpdatePasswordRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UserRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UpdateUserInfoRequestDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.model.User;
import java.util.List;

public interface UserService {
    UserResponseDTO register(UserRequestDTO user);
    void login(LoginRequestDTO loginRequest);
    void updatePassword(Long userId, UpdatePasswordRequestDTO updatePasswordRequest);
    UserResponseDTO updateUserInformation(Long id, UpdateUserInfoRequestDTO updatedUserInfo);
    boolean adminExists();
    void registerAdmin(User admin);
    UserResponseDTO userInfo(Long id);
    User saveUser(User user);
    User getUserById(Long Id);
    List<UserResponseDTO> usersList();
}
