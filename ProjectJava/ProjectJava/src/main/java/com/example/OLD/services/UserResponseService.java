//package com.example.OLD.services;
//
//import com.example.OLD.entities.UserResponse;
//import com.example.OLD.repositories.UserResponseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserResponseService {
//
//    @Autowired
//    private UserResponseRepository userResponseRepository;
//
//    public UserResponse saveResponse(UserResponse response) {
//        return userResponseRepository.save(response);
//    }
//
//    public List<UserResponse> getUserResponses(Long userId) {
//        return userResponseRepository.findByUserId(userId);
//    }
//}
