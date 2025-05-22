package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.RequestDTO.LoginRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UpdatePasswordRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UserRequestDTO;
import com.vena_project.crowd_funding.dto.RequestDTO.UpdateUserInfoRequestDTO;
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
    public UserResponseDTO updateUserInformation(Long id, UpdateUserInfoRequestDTO updatedUserInfo) {
        User user = getUserById(id);

        if(!user.getEmail().equals(updatedUserInfo.getEmail())){
            logger.info("name changed from : {} to {}", user.getEmail(), updatedUserInfo.getEmail());
            user.setEmail(updatedUserInfo.getEmail());
        }

        if(!user.getName().equals(updatedUserInfo.getName())){
            logger.info("name changed from : {} to {}", user.getName(), updatedUserInfo.getName());
            user.setName(updatedUserInfo.getName());
        }
        userRepository.save(user);
        logger.info("User updated: {}, email: {}", updatedUserInfo.getName(), updatedUserInfo.getEmail());

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.convertToDTO(user);
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO userDTO) {
        if(userDTO.getRole() == UserRole.ADMIN){
            throw new IllegalArgumentException("Admin cannot be registered, submit a request first.");
        }

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.updateFromRequestDTO(userDTO);
        userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.convertToDTO(user);
        logger.info("User registered: {}", user.getEmail());
        return userResponseDTO;
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
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("user with the id : "+id+" could not be found"));
    }

    @Override
    public UserResponseDTO userInfo(Long id) {
        User user = getUserById(id);

        if(user.getRole() == UserRole.ADMIN){
            throw new IllegalArgumentException("Admin cannot be accessed");
        }

        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.convertToDTO(user);
        return userDTO;
    }

    @Override
    public void updatePassword(Long userId, UpdatePasswordRequestDTO updatePasswordRequest) {
        User user = getUserById(userId);

        if(!user.getPassword().equals(updatePasswordRequest.getOldPassword())){
            throw new IllegalArgumentException("invalid old password provided");
        }

        user.setPassword(updatePasswordRequest.getNewPassword());
        userRepository.save(user);
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
