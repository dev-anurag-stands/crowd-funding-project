package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.RequestDTO.LoginRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UserRequestDTO;
import com.vena_project.crowd_funding.dto.UpdateUserInfoDTO;
import com.vena_project.crowd_funding.dto.ResponseDTO.UserResponseDTO;
import com.vena_project.crowd_funding.exception.ResourceNotFoundException;
import com.vena_project.crowd_funding.model.User;
import com.vena_project.crowd_funding.model.enums.UserRole;
import com.vena_project.crowd_funding.repository.UserRepository;
import com.vena_project.crowd_funding.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDTO> usersList() {
            return userRepository.findAll()
                    .stream()
                    .map(user -> {
                        UserResponseDTO userDTO = new UserResponseDTO();
                        userDTO.convertToDTO(user);
                        return userDTO;
                    })
                    .collect(Collectors.toList());
    }

    @Override
    public void updateUserInformation(Long id, UpdateUserInfoDTO updatedUserInfo) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new ResourceNotFoundException("invalid user id");
        }

        logger.info("name changed from : {} to {}", user.getEmail(), updatedUserInfo.getEmail());
        user.setEmail(updatedUserInfo.getEmail());

        logger.info("name changed from : {} to {}", user.getName(), updatedUserInfo.getName());
        user.setName(updatedUserInfo.getName());

        userRepository.save(user);
        logger.info("User updated: {}, email: {}", updatedUserInfo.getName(), updatedUserInfo.getEmail());
    }

    @Override
    public void register(UserRequestDTO userDTO) {
        if(userDTO.getRole() == UserRole.ADMIN){
            throw new IllegalArgumentException("Admin cannot be registered, submit a request first.");
        }

        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.updateFromRequestDTO(userDTO);
        userRepository.save(user);
        logger.info("User registered: {}", user.getEmail());
    }

    @Override
    public void login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("email not found in the database"));

        // Compare password (plain text comparison for now)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        logger.info("User logged in: {}", user.getName());
    }

    @Override
    public UserResponseDTO userInfo(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user != null){
            throw new ResourceNotFoundException("Invalid user id.");
        }

        if(user.getRole() == UserRole.ADMIN){
            throw new IllegalArgumentException("Admin cannot be accessed");
        }

        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.convertToDTO(user);
        return userDTO;
    }

    @Override
    public boolean adminExists() {
        return userRepository.existsByRole(UserRole.ADMIN);
    }

    @Override
    public void registerAdmin(User admin) {
        userRepository.save(admin);
        logger.info("Admin registered: {}", admin.getEmail());
    }

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }
}
