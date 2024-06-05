package com.example.ProjectJava.entities;

import jakarta.persistence.*;
@Entity
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // Getters and setters
}
