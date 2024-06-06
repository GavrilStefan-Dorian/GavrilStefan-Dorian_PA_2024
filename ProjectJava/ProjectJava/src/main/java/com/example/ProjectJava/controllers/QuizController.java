package com.example.ProjectJava.controllers;

import com.example.ProjectJava.entities.Quiz;
import com.example.ProjectJava.entities.UserResponse;
import com.example.ProjectJava.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestParam Long domainId, @RequestParam List<Long> questionIds) {
        Quiz quiz = quizService.createQuiz(domainId, questionIds);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/{quizId}/start")
    public ResponseEntity<Void> startQuiz(@PathVariable("quizId") Long quizId, @RequestParam Long userId) {
        quizService.startQuiz(quizId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<Void> submitResponses(@PathVariable("quizId") Long userQuizId, @RequestBody List<UserResponse> responses) {
        quizService.submitResponses(userQuizId, responses);
        return ResponseEntity.ok().build();
    }
}
