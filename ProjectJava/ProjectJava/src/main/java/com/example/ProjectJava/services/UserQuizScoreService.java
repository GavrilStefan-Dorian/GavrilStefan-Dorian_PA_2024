package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.UserQuizScore;
import com.example.ProjectJava.repositories.UserQuizScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQuizScoreService {

    @Autowired
    private UserQuizScoreRepository userQuizScoreRepository;

    public UserQuizScore saveScore(UserQuizScore score) {
        return userQuizScoreRepository.save(score);
    }

    public List<UserQuizScore> getScoresByUser(Long userId) {
        return userQuizScoreRepository.findByUserId(userId);
    }

    public List<UserQuizScore> getScoresByQuiz(Long quizId) {
        return userQuizScoreRepository.findByQuizId(quizId);
    }
}
