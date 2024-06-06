package com.example.ProjectJava.entities;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name="user_quizzes")
public class UserQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userQuizId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private Integer score;

    public Long getUserQuizId() {
        return userQuizId;
    }

    public void setUserQuizId(Long userQuizId) {
        this.userQuizId = userQuizId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    // Getters and setters
}