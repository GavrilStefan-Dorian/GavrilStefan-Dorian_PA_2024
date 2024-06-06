//package com.example.OLD.services;
//
//import com.example.OLD.entities.Quiz;
//import com.example.OLD.entities.UserQuizScore;
//import com.example.OLD.entities.UserResponse;
//import com.example.OLD.repositories.UserQuizScoreRepository;
//import com.example.OLD.repositories.UserRepository;
//import com.example.OLD.repositories.UserResponseRepository;
//import org.apache.catalina.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserQuizScoreRepository userQuizScoreRepository;
//
//    @Autowired
//    private UserResponseRepository userResponseRepository;
//
//    // Get all users
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    // Get user by ID
//    public Optional<User> getUserById(Long userId) {
//        return userRepository.findById(userId);
//    }
//
//    // Get quizzes for a user
//    public List<Quiz> getQuizzesForUser(Long userId) {
//        // Implementation logic
//    }
//
//    // Get a specific quiz for a user
//    public Optional<Quiz> getQuizForUser(Long userId, Long quizId) {
//        // Implementation logic
//    }
//
//    // Get score for a specific quiz for a user
//    public Optional<UserQuizScore> getScoreForQuiz(Long userId, Long quizId) {
//        // Implementation logic
//    }
//
//    // Get responses for a specific quiz for a user
//    public List<UserResponse> getResponsesForQuiz(Long userId, Long quizId) {
//        // Implementation logic
//    }
//
//    // Get a specific response for a specific quiz for a user
//    public Optional<UserResponse> getResponseForQuiz(Long userId, Long quizId, Long responseId) {
//        // Implementation logic
//    }
//
//
//    public void submitResponsesForQuiz(Long userId, Long quizId, List<UserResponse> responses) {
//        // Save each response
//        for (UserResponse response : responses) {
//            response.setUserId(userId);
//            response.setQuizId(quizId);
//            userResponseRepository.save(response);
//        }
//        // Calculate and save the score
//        int score = calculateScore(responses);
//        UserQuizScore userQuizScore = new UserQuizScore(userId, quizId, score);
//        userQuizScoreRepository.save(userQuizScore);
//    }
//
//    public void saveQuizToHistory(Long userId, Long quizId) {
//        // Logic to save quiz to user's history
//        UserQuiz userQuiz = new UserQuiz(userId, quizId);
//        userQuizRepository.save(userQuiz);
//    }
//
//    private int calculateScore(List<UserResponse> responses) {
//        // Logic to calculate score based on responses
//        int score = 0;
//        // Example scoring logic
//        for (UserResponse response : responses) {
//            if (response.isCorrect()) {
//                score += 1;
//            }
//        }
//        return score;
//    }
//}
