package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {
    List<UserQuiz> findByUserUserId(Long userId);

    Optional<UserQuiz> findByUserUserIdAndQuizQuizId(Long userId, Long quizId);
}
