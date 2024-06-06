package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {
    List<UserQuiz> findByUserId(Long userId);
}
