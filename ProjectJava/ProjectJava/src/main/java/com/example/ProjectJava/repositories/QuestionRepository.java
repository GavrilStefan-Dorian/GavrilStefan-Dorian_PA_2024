package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDomainDomainId(Long domainId);

//    Optional<Question> findByQuestionIdAndDomainDomainId(Long questionId, Long domainId);
//
//    List<Question> findByQuizQuizId(Long quizId);
    List<Question> findByQuizQuestionsQuizQuizId(Long quizId);

//    List<Question> findByQuizId(Long quizId);

//    List<Question> findByQuizQuizId(Long quizId);
}
