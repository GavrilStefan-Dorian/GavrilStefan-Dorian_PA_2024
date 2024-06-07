package com.example.ProjectJava.controllers;

import com.example.ProjectJava.entities.Domain;
import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.services.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/domains")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @GetMapping
    public ResponseEntity<List<Domain>> getAllDomains() {
        List<Domain> domains = domainService.getAllDomains();
        return ResponseEntity.ok(domains);
    }

    @GetMapping("/{domainId}")
    public ResponseEntity<Domain> getDomainById(@PathVariable("domainId") Long domainId) {
        Domain domain = domainService.getDomainById(domainId).get();
        return ResponseEntity.ok(domain);
    }

    @GetMapping("/{domainId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByDomain(@PathVariable("domainId") Long domainId) {
        List<Question> questions = domainService.getQuestionsByDomain(domainId);
        return ResponseEntity.ok(questions);
    }
}
