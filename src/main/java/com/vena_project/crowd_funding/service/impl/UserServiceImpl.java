package com.vena_project.crowd_funding.service.impl;

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
}
