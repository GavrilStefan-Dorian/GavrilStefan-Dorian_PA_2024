//package com.example.OLD.services;
//
//
//import com.example.OLD.entities.Domain;
//import com.example.OLD.entities.Question;
//import com.example.OLD.entities.UserResponse;
//import com.example.OLD.repositories.DomainRepository;
//import com.example.OLD.repositories.QuestionRepository;
//import com.example.OLD.repositories.UserResponseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class DomainService {
//
//    @Autowired
//    private DomainRepository domainRepository;
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Autowired
//    private UserResponseRepository userResponseRepository;
//
//    // Get all domains
//    public List<Domain> getAllDomains() {
//        return domainRepository.findAll();
//    }
//
//    // Get domain by ID
//    public Optional<Domain> getDomainById(Long domainId) {
//        return domainRepository.findById(domainId);
//    }
//
//    // Get all questions
//    public List<Question> getAllQuestions() {
//        return questionRepository.findAll();
//    }
//
//    // Get questions by domain ID
//    public List<Question> getQuestionsByDomain(Long domainId) {
//        return questionRepository.findByDomainId(domainId);
//    }
//
//    // Get question by domain ID and question ID
//    public Optional<Question> getQuestionById(Long domainId, Long questionId) {
//        return questionRepository.findByIdAndDomainId(questionId, domainId);
//    }
//
//    // Get responses for a question in a domain
//    public List<UserResponse> getResponsesForQuestion(Long domainId, Long questionId) {
//        return userResponseRepository.findByDomainIdAndQuestionId(domainId, questionId);
//    }
//}
