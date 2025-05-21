package com.vena_project.crowd_funding.config;

import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer  {
    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initAdmin() {
    System.out.println(" DataInitializer running...");
        if (!userService.adminExists()) {
            User admin = new User();

            admin.setName("admin1");
            admin.setEmail("admin1@gmail.com");
            admin.setPassword("admin123");
            admin.setRole(UserRole.ADMIN);

            userService.registerAdmin(admin);
            System.out.println("Default admin user created.");
        } else {
            System.out.println("Admin user already exists, skipping creation.");
        }
    }
}

