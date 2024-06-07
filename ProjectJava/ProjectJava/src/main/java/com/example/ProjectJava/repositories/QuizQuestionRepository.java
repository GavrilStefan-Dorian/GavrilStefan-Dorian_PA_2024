package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.Quiz;
import com.example.ProjectJava.entities.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
}
