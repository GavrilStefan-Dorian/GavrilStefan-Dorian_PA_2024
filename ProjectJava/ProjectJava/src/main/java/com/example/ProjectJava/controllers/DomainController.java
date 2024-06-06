package com.example.ProjectJava.controllers;

import com.example.ProjectJava.entities.Domain;
import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/domains")
public class DomainController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Domain>> getAllDomains() {
        List<Domain> domains = quizService.getAllDomains();
        return ResponseEntity.ok(domains);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domain> getDomainById(@PathVariable("id") Long domainId) {
        Optional<Domain> domain = quizService.getDomainById(domainId);
        return domain.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getQuestionsByDomain(@PathVariable("id") Long domainId) {
        List<Question> questions = quizService.getQuestionsByDomain(domainId);
        return ResponseEntity.ok(questions);
    }
}
