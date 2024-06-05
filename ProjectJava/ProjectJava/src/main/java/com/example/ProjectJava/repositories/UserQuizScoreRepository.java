package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.UserQuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizScoreRepository extends JpaRepository<UserQuizScore, Long> {
    List<UserQuizScore> findByUserId(Long userId);
    List<UserQuizScore> findByQuizId(Long quizId);
}
