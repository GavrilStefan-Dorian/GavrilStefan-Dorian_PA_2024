//package com.example.OLD.controllers;
//
//import com.example.OLD.entities.Quiz;
//import com.example.OLD.entities.UserQuizScore;
//import com.example.OLD.entities.UserResponse;
//import com.example.OLD.services.UserService;
//import org.apache.catalina.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // Get all users
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//
//    // Get user by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
//        Optional<User> user = userService.getUserById(userId);
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Get quizzes for a user
//    @GetMapping("/{id}/quizzes")
//    public ResponseEntity<List<Quiz>> getQuizzesForUser(@PathVariable("id") Long userId) {
//        List<Quiz> quizzes = userService.getQuizzesForUser(userId);
//        return ResponseEntity.ok(quizzes);
//    }
//
//    // Get a specific quiz for a user
//    @GetMapping("/{id}/quizzes/{quizId}")
//    public ResponseEntity<Quiz> getQuizForUser(@PathVariable("id") Long userId, @PathVariable("quizId") Long quizId) {
//        Optional<Quiz> quiz = userService.getQuizForUser(userId, quizId);
//        return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Get score for a specific quiz for a user
//    @GetMapping("/{id}/quizzes/{quizId}/score")
//    public ResponseEntity<UserQuizScore> getScoreForQuiz(@PathVariable("id") Long userId, @PathVariable("quizId") Long quizId) {
//        Optional<UserQuizScore> score = userService.getScoreForQuiz(userId, quizId);
//        return score.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Get responses for a specific quiz for a user
//    @GetMapping("/{id}/quizzes/{quizId}/responses")
//    public ResponseEntity<List<UserResponse>> getResponsesForQuiz(@PathVariable("id") Long userId, @PathVariable("quizId") Long quizId) {
//        List<UserResponse> responses = userService.getResponsesForQuiz(userId, quizId);
//        return ResponseEntity.ok(responses);
//    }
//
//    // Get a specific response for a specific quiz for a user
//    @GetMapping("/{id}/quizzes/{quizId}/responses/{responseId}")
//    public ResponseEntity<UserResponse> getResponseForQuiz(@PathVariable("id") Long userId, @PathVariable("quizId") Long quizId, @PathVariable("responseId") Long responseId) {
//        Optional<UserResponse> response = userService.getResponseForQuiz(userId, quizId, responseId);
//        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Submit answers for a specific quiz for a user
//    @PostMapping("/{id}/quizzes/{quizId}/responses")
//    public ResponseEntity<Void> submitResponsesForQuiz(
//            @PathVariable("id") Long userId,
//            @PathVariable("quizId") Long quizId,
//            @RequestBody List<UserResponse> responses) {
//        userService.submitResponsesForQuiz(userId, quizId, responses);
//        return ResponseEntity.ok().build();
//    }
//
//    // Save quiz to user's history
//    @PostMapping("/{id}/quizzes/{quizId}/history")
//    public ResponseEntity<Void> saveQuizToHistory(
//            @PathVariable("id") Long userId,
//            @PathVariable("quizId") Long quizId) {
//        userService.saveQuizToHistory(userId, quizId);
//        return ResponseEntity.ok().build();
//    }
//}
//}
