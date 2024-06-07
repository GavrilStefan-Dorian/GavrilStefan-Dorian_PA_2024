package com.example.ProjectJava.controllers;

import com.example.ProjectJava.entities.Answer;
import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{questionId}/answers")
    public ResponseEntity<List<Answer>> getQuestionAnswers(@PathVariable("questionId") Long questionId) {
        List<Answer> answers = questionService.getQuestionAnswers(questionId);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("questionId") Long questionId) {
        Question question = questionService.getQuestionById(questionId).get();
        return ResponseEntity.ok(question);
    }
}
