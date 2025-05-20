package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.LoginRequestDTO;
import com.vena_project.crowd_funding.dto.UpdateUserInfoDTO;
import com.vena_project.crowd_funding.dto.UserDTO;
import com.vena_project.crowd_funding.exception.ResourceNotFoundException;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.repository.UserRepository;
import com.vena_project.crowd_funding.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> usersList() {
        return userRepository.findAll();
    }

    @Override
    public void updateUserInformation(UpdateUserInfoDTO updatedUserInfo) {
        if(updatedUserInfo.getName() == null
        || updatedUserInfo.getEmail() == null
        || updatedUserInfo.getEmail().isBlank()
        || updatedUserInfo.getName().isBlank()){
            throw new IllegalArgumentException("Invalid user information");
        }

        userRepository.findByEmail(updatedUserInfo.getEmail()).ifPresent(user -> {
            {
                user.setName(updatedUserInfo.getName());
                userRepository.save(user);
            }
        });
        System.out.println("user updated : "+updatedUserInfo.getName()+", email"+updatedUserInfo.getEmail());
    }

    @Override
    public void register(UserDTO userDTO) {
        if (userDTO == null ||
                userDTO.getName() == null || userDTO.getName().isBlank() ||
                userDTO.getEmail() == null || userDTO.getEmail().isBlank() ||
                userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("Invalid user: name, email, password, Role must not be null or blank.");
        }

        if(userDTO.getRole() == UserRole.ADMIN){
            throw new IllegalArgumentException("Admin cannot be registered, submit a request first.");
        }

        // Check if email is already taken
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Convert DTO to Entity
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // You may want to hash this!

        userRepository.save(user);
    }

    @Override
    public void login(LoginRequestDTO loginRequest) {
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

    @Override
    public User userInfo(Long id) {
        if(userRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("invalid user id");
        }

        return userRepository.findById(id).get();
    }
}
