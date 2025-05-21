package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.RequestDTO.LoginRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UserRequestDTO;
import com.vena_project.crowd_funding.dto.UpdateUserInfoDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDTO> usersList();
    void updateUserInformation(Long id, UpdateUserInfoDTO updatedUserInfo);
    void register(UserRequestDTO user);
    void login(LoginRequestDTO loginRequest);
    UserResponseDTO userInfo(Long id);
    boolean adminExists();
    void registerAdmin(User admin);
    User saveUser(User user);
    User getUserById(Long Id);
}
