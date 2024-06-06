package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
