package com.vena_project.crowd_funding.config;

import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.service.UserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initAdmin() {
        logger.info("DataInitializer running...");
        if (!userService.adminExists()) {
            User admin = new User();

            admin.setName("admin1");
            admin.setEmail("admin1@gmail.com");
            admin.setPassword("admin123");
            admin.setRole(UserRole.ADMIN);

            userService.registerAdmin(admin);
            logger.info("Default admin user created.");
        } else {
            logger.info("Admin user already exists, skipping creation.");
        }
    }
}
