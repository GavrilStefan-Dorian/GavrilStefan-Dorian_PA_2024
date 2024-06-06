// QuizService.java
package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.Domain;
import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.entities.Quiz;
import com.example.ProjectJava.entities.QuizQuestion;
import com.example.ProjectJava.repositories.DomainRepository;
import com.example.ProjectJava.repositories.QuestionRepository;
import com.example.ProjectJava.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Quiz createQuiz(Long domainId, int numberOfQuestions) {
        Domain domain = domainRepository.findById(domainId).orElseThrow();
        List<Question> allQuestions = questionRepository.findByDomainDomainId(domainId);

        // If there are not enough questions, return null or handle accordingly
        if (allQuestions.size() < numberOfQuestions) {
            return null;
        }

        List<QuizQuestion> quizQuestions = selectRandomQuestions(allQuestions, numberOfQuestions);

        Quiz quiz = new Quiz();
        quiz.setDomain(domain);
        quiz.setQuizQuestions(quizQuestions);

        return quizRepository.save(quiz);
    }

    private List<QuizQuestion> selectRandomQuestions(List<Question> allQuestions, int numberOfQuestions) {
        List<QuizQuestion> selectedQuizQuestions = new ArrayList<>();
        Random random = new Random();

        while (selectedQuizQuestions.size() < numberOfQuestions) {
            Question randomQuestion = allQuestions.get(random.nextInt(allQuestions.size()));

            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setQuestion(randomQuestion);

            selectedQuizQuestions.add(quizQuestion);
        }

        return selectedQuizQuestions;
    }

}
