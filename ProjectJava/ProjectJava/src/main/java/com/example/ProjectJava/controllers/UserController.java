package com.example.ProjectJava.controllers;

import com.example.ProjectJava.entities.User;
import com.example.ProjectJava.entities.Quiz;
import com.example.ProjectJava.entities.UserQuiz;
import com.example.ProjectJava.entities.UserResponse;
import com.example.ProjectJava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId).get();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/quizzes")
    public ResponseEntity<List<UserQuiz>> getUserQuizzes(@PathVariable("userId") Long userId) {
        List<UserQuiz> quizzes = userService.getUserQuizzes(userId);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{userId}/quizzes/{quizId}")
    public ResponseEntity<Quiz> getUserSpecificQuiz(@PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId) {
        Quiz quiz = userService.getUserSpecificQuiz(userId, quizId);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{userId}/quizzes/{quizId}/responses")
    public ResponseEntity<List<UserResponse>> getUserQuizResponses(@PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId) {
        List<UserResponse> responses = userService.getUserQuizResponses(userId, quizId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{userId}/quizzes/{quizId}/responses")
    public ResponseEntity<Void> postUserQuizResponses(@PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId, @RequestBody List<UserResponse> responses) {
        userService.postUserQuizResponses(userId, quizId, responses);
        return ResponseEntity.ok().build();
    }
}
