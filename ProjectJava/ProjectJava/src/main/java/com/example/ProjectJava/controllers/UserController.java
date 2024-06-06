package com.example.ProjectJava.controllers;

import com.example.ProjectJava.entities.User;
import com.example.ProjectJava.entities.UserQuiz;
import com.example.ProjectJava.entities.UserResponse;
import com.example.ProjectJava.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = quizService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        Optional<User> user = quizService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/quizzes")
    public ResponseEntity<List<UserQuiz>> getUserQuizzes(@PathVariable("id") Long userId) {
        List<UserQuiz> quizzes = quizService.getUserQuizzes(userId);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{id}/quizzes/{quizId}/responses")
    public ResponseEntity<List<UserResponse>> getQuizResponses(@PathVariable("id") Long userQuizId) {
        List<UserResponse> responses = quizService.getQuizResponses(userQuizId);
        return ResponseEntity.ok(responses);
    }
}
