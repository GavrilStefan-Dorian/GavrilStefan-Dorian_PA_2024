package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionQuestionId(Long questionId);
}

