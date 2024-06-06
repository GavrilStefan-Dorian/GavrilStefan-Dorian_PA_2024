package com.example.ProjectJava.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="users",schema="public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserQuiz> quizzes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserQuiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<UserQuiz> quizzes) {
        this.quizzes = quizzes;
    }

    // Constructors, getters, setters
}
