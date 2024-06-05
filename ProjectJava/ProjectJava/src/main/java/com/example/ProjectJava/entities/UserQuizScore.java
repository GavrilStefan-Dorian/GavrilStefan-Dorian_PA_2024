package com.example.ProjectJava.entities;

import jakarta.persistence.*;

@Entity
public class UserQuizScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long quizId;
    private int score;

    // Getters and setters
}
