package com.vena_project.crowd_funding.service;

import com.vena_project.crowd_funding.dto.UserInfoDTO;
import com.vena_project.crowd_funding.model.User;

import java.util.List;

public interface AdminService {
    String upgradeUserToAdmin(Long userId);
    List<UserInfoDTO> getAllUsers();
}
