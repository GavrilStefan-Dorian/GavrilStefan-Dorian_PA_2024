//package com.example.OLD.entities;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Answer {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String text;
//    private boolean isCorrect;
//
//    @ManyToOne
//    @JoinColumn(name = "question_id")
//    private Question question;
//
//    // Getters and setters
//}
