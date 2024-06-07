package com.example.ProjectJava.controllers;

import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.entities.Quiz;
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
    public ResponseEntity<Quiz> createQuiz(@RequestParam Long domainId, @RequestParam int numberOfQuestions) {
        System.out.println("Received request to create quiz with domainId: " + domainId + " and numberOfQuestions: " + numberOfQuestions);
        Quiz quiz = quizService.createQuiz(domainId, numberOfQuestions);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuizQuestions(@PathVariable Long quizId) {
        List<Question> questions = quizService.getQuizQuestions(quizId);
        return ResponseEntity.ok(questions);
    }
}
