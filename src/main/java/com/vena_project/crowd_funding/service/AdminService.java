package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.model.User;

import java.util.List;

public interface AdminService {
    User registerAdminUser(User user);
    List<User> getAllUsers();
}
