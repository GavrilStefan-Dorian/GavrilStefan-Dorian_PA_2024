package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {
    List<UserResponse> findByQuizQuizId(Long userQuizId);

    List<UserResponse> findByUserUserIdAndQuizQuizId(Long userId, Long quizId);
}
