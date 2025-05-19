package com.vena_project.crowd_funding.service.impl;

import com.vena_project.crowd_funding.dto.UpdateUserInfo;
import com.vena_project.crowd_funding.model.User;
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
    public void updateUserInformation(UpdateUserInfo updatedUserInfo) {
        if(updatedUserInfo.getName() == null
        || updatedUserInfo.getEmail() == null
        || updatedUserInfo.getPassword() == null
        || updatedUserInfo.getEmail().isBlank()
        || updatedUserInfo.getName().isBlank()
        || updatedUserInfo.getPassword().isBlank()){
            throw new IllegalArgumentException("Invalid user information");
        }

        userRepository.findByEmail(updatedUserInfo.getEmail()).ifPresent(user -> {
            if(user.getPassword().equals(updatedUserInfo.getPassword())){
                user.setName(updatedUserInfo.getName());
                userRepository.save(user);
            }
        });
        System.out.println("user updated : "+updatedUserInfo.getName()+", email"+updatedUserInfo.getEmail());
    }
}
