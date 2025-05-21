package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.UserInfoDTO;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.AdminService;
import com.vena_project.crowd_funding.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private UserService userService;

    public AdminServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public String upgradeUserToAdmin(Long userId) {
        User user = userService.userInfo(userId);
        user.setRole(UserRole.ADMIN);
        userService.saveUser(user);
        return "User with id " + userId + " upgraded to ADMIN successfully.";
    }

    @Override
    public List<UserInfoDTO> getAllUsers() {
        return userService.usersList();
    }

    @Override
    public List<UserInfoDTO> getUsersByRole(String role) {
        List<UserInfoDTO> allUsers = userService.usersList();
        return allUsers.stream()
                .filter(user -> {
                    System.out.println(user.getRole().toString().equalsIgnoreCase(role));
                    return user.getRole().toString().equalsIgnoreCase(role);
                })
                .collect(Collectors.toList());
    }
}