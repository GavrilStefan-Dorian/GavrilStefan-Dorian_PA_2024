//package com.example.OLD.services;
//
//import com.example.OLD.entities.Answer;
//import com.example.OLD.repositories.AnswerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AnswerService {
//
//    @Autowired
//    private AnswerRepository answerRepository;
//
//    public List<Answer> getAnswersByQuestion(Long questionId) {
//        return answerRepository.findByQuestionId(questionId);
//    }
//}
