package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.LoginRequest;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.repository.UserRepository;
import com.vena_project.crowd_funding.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        if (user == null ||
                user.getName() == null || user.getName().isBlank() ||
                user.getEmail() == null || user.getEmail().isBlank() ||
                user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Invalid user: name, email, and password must not be null or blank.");
        }

        // Check if email is already taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        userRepository.save(user);
    }

    @Override
    public void login(LoginRequest loginRequest) {
        // Null and blank validation
        if (loginRequest == null ||
                loginRequest.getEmail() == null || loginRequest.getEmail().isBlank() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            throw new IllegalArgumentException("Email and password must not be null or blank.");
        }

        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Compare password (plain text comparison for now)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        System.out.println("user logged in : "+user.getName());
    }
}
