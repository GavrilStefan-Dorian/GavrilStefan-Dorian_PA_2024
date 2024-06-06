// QuestionService.java
package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.Answer;
import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.repositories.AnswerRepository;
import com.example.ProjectJava.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public List<Answer> getQuestionAnswers(Long questionId) {
        return answerRepository.findByQuestionQuestionId(questionId);
    }

}
