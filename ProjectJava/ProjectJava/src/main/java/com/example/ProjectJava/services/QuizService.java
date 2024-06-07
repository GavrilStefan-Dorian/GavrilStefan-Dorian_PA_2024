package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.Domain;
import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.entities.Quiz;
import com.example.ProjectJava.entities.QuizQuestion;
import com.example.ProjectJava.repositories.DomainRepository;
import com.example.ProjectJava.repositories.QuestionRepository;
import com.example.ProjectJava.repositories.QuizQuestionRepository;
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

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    public Quiz createQuiz(Long domainId, int numberOfQuestions) {
        Domain domain = domainRepository.findById(domainId).orElseThrow();
        List<Question> allQuestions = questionRepository.findByDomainDomainId(domainId);

        if (allQuestions.size() < numberOfQuestions) {
            return null;
        }

        Quiz quiz = new Quiz();
        List<QuizQuestion> quizQuestions = selectRandomQuestions(quiz, allQuestions, numberOfQuestions);

        quiz.setDomain(domain);
        quiz.setNoQuestions(quizQuestions.size());

        quiz = quizRepository.save(quiz);

        for( QuizQuestion quizQuestion : quizQuestions)
            quizQuestion.setQuiz((quiz));

        quizQuestionRepository.saveAll(quizQuestions);

        return quiz;
    }

    private List<QuizQuestion> selectRandomQuestions(Quiz quiz, List<Question> allQuestions, int numberOfQuestions) {
        List<QuizQuestion> selectedQuizQuestions = new ArrayList<>();
        Random random = new Random();

        while (selectedQuizQuestions.size() < numberOfQuestions) {
            Question randomQuestion = allQuestions.get(random.nextInt(allQuestions.size()));

            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setQuestion(randomQuestion);
//            quizQuestion.setQuiz(quiz);
            selectedQuizQuestions.add(quizQuestion);
        }

        return selectedQuizQuestions;
    }

    public List<Question> getQuizQuestions(Long quizId) {
        return questionRepository.findByQuizQuestionsQuizQuizId(quizId);
    }
}
