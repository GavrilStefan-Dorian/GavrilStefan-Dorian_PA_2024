package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestionsByDomain(Long domainId) {
        return questionRepository.findByDomainId(domainId);
    }
}
