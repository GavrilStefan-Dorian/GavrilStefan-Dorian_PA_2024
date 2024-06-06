package com.example.ProjectJava.entities;

import jakarta.persistence.*;

@Entity
@Table(name="user_responses",schema="public")
public class UserResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_quiz_id")
    private UserQuiz userQuiz;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String response;
    private boolean isCorrect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserQuiz getUserQuiz() {
        return userQuiz;
    }

    public void setUserQuiz(UserQuiz userQuiz) {
        this.userQuiz = userQuiz;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
