package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.LoginRequest;
import com.vena_project.crowd_funding.model.User;

public interface AuthService {
    void register(User user);
    void login(LoginRequest loginRequest);
}
