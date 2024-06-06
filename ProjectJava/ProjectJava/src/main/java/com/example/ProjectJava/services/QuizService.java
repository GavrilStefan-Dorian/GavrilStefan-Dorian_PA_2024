package com.example.ProjectJava.services;

import com.example.ProjectJava.entities.*;
import com.example.ProjectJava.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserQuizRepository userQuizRepository;

    @Autowired
    private UserResponseRepository userResponseRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<Domain> getAllDomains() {
        return domainRepository.findAll();
    }

    public Optional<Domain> getDomainById(Long domainId) {
        return domainRepository.findById(domainId);
    }

    public List<Question> getQuestionsByDomain(Long domainId) {
        return questionRepository.findByDomainId(domainId);
    }

    public Optional<Question> getQuestionById(Long domainId, Long questionId) {
        return questionRepository.findByIdAndDomainId(questionId, domainId);
    }

    public Quiz createQuiz(Long domainId, List<Long> questionIds) {
        Domain domain = domainRepository.findById(domainId).orElseThrow();
        List<Question> questions = questionRepository.findAllById(questionIds);
        Quiz quiz = new Quiz();
        quiz.setDomain(domain);
        quiz.setQuestions(questions);
        return quizRepository.save(quiz);
    }

    public void startQuiz(Long quizId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        UserQuiz userQuiz = new UserQuiz();
        userQuiz.setUser(user);
        userQuiz.setQuiz(quiz);
        userQuizRepository.save(userQuiz);
    }

    public void submitResponses(Long userQuizId, List<UserResponse> responses) {
        UserQuiz userQuiz = userQuizRepository.findById(userQuizId).orElseThrow();
        int score = 0;
        for (UserResponse response : responses) {
            response.setUserQuiz(userQuiz);
            if (response.isCorrect()) {
                score++;
            }
            userResponseRepository.save(response);
        }
        userQuiz.setScore(score);
        userQuizRepository.save(userQuiz);
    }

    public List<UserQuiz> getUserQuizzes(Long userId) {
        return userQuizRepository.findByUserId(userId);
    }

    public List<UserResponse> getQuizResponses(Long userQuizId) {
        return userResponseRepository.findByUserQuizId(userQuizId);
    }
}
