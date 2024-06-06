// UserService.java
package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.Quiz;
import com.example.ProjectJava.entities.User;
import com.example.ProjectJava.entities.UserQuiz;
import com.example.ProjectJava.entities.UserResponse;
import com.example.ProjectJava.repositories.QuizRepository;
import com.example.ProjectJava.repositories.UserRepository;
import com.example.ProjectJava.repositories.UserQuizRepository;
import com.example.ProjectJava.repositories.UserResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserQuizRepository userQuizRepository;


    @Autowired
    private UserResponseRepository userResponseRepository;

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<UserQuiz> getUserQuizzes(Long userId) {
        return userQuizRepository.findByUserUserId(userId);
    }

    public Optional<UserQuiz> getUserQuizById(Long userId, Long quizId) {
        return userQuizRepository.findByUserUserIdAndQuizQuizId(userId, quizId);
    }

    public List<UserResponse> getUserQuizResponses(Long userId, Long quizId) {
        return userResponseRepository.findByUserUserIdAndQuizQuizId(userId, quizId);
    }

    public Quiz getUserSpecificQuiz(Long userId, Long quizId) {
        Optional<UserQuiz> userQuizOptional = userQuizRepository.findByUserUserIdAndQuizQuizId(userId, quizId);
        if (userQuizOptional.isPresent()) {
            UserQuiz userQuiz = userQuizOptional.get();
            return userQuiz.getQuiz();
        } else {
            return null;
        }
    }

    public void postUserQuizResponses(Long userId, Long quizId, List<UserResponse> responses) {
        // Retrieve user and quiz from database
        User user = userRepository.findById(userId).orElse(null);
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if (user != null && quiz != null) {
            // Save user responses
            for (UserResponse response : responses) {
                response.setUser(user);
                response.setQuiz(quiz);
                userResponseRepository.save(response);
            }
        }
    }
}
