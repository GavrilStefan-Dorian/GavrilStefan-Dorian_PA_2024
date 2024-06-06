// DomainService.java
package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.Domain;
import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.repositories.DomainRepository;
import com.example.ProjectJava.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainService {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<Domain> getAllDomains() {
        return domainRepository.findAll();
    }

    public Optional<Domain> getDomainById(Long domainId) {
        return domainRepository.findById(domainId);
    }

    public List<Question> getQuestionsByDomain(Long domainId) {
        return questionRepository.findByDomainDomainId(domainId);
    }

}
