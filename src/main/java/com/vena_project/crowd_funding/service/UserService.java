package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.LoginRequest;
import com.vena_project.crowd_funding.dto.UpdateUserInfo;
import com.vena_project.crowd_funding.model.User;
import java.util.List;

public interface UserService {
    List<User> usersList();
    void updateUserInformation(UpdateUserInfo updatedUserInfo);
    void register(User user);
    void login(LoginRequest loginRequest);
}
