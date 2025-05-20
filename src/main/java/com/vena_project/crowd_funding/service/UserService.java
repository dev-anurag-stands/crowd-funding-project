package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.LoginRequestDTO;
import com.vena_project.crowd_funding.dto.UpdateUserInfoDTO;
import com.vena_project.crowd_funding.dto.UserDTO;
import com.vena_project.crowd_funding.model.User;
import java.util.List;

public interface UserService {
    List<User> usersList();
    void updateUserInformation(UpdateUserInfoDTO updatedUserInfo);
    void register(UserDTO user);
    void login(LoginRequestDTO loginRequest);
    User userInfo(Long id);
}
