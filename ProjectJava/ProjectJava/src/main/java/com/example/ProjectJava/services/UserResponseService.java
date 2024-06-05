package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.UserResponse;
import com.example.ProjectJava.repositories.UserResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResponseService {

    @Autowired
    private UserResponseRepository userResponseRepository;

    public UserResponse saveResponse(UserResponse response) {
        return userResponseRepository.save(response);
    }

    public List<UserResponse> getUserResponses(Long userId) {
        return userResponseRepository.findByUserId(userId);
    }
}
