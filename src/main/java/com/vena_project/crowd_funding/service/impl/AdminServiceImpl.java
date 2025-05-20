package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.AdminService;
import com.vena_project.crowd_funding.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private UserService userService;

    public AdminServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User registerAdminUser(User user) {
       if(user.getRole()!= UserRole.ADMIN){
           throw new IllegalArgumentException("Only Admins users can be registed by Admin ");
       }
       return userService.register(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
