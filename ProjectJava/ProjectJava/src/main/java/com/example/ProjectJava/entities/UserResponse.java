package com.example.ProjectJava.entities;

import jakarta.persistence.*;

@Entity
public class UserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long questionId;
    private Long answerId;

    // Getters and setters
}
