package com.example.ProjectJava.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="quizzes",schema="public")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz")
    private List<UserQuiz> userQuizzes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<UserQuiz> getUserQuizzes() {
        return userQuizzes;
    }

    public void setUserQuizzes(List<UserQuiz> userQuizzes) {
        this.userQuizzes = userQuizzes;
    }

    // Constructors, getters, setters
}
