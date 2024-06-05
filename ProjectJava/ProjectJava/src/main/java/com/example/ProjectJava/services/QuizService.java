
package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.entities.Quiz;
import com.example.ProjectJava.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuiz(Long quizId) {
        return quizRepository.findById(quizId);
    }

    public List<Question> getQuestionsForQuiz(Long quizId) {
        return quizRepository.findById(quizId)
                .map(Quiz::getQuestions)
                .orElse(null);
    }
}
