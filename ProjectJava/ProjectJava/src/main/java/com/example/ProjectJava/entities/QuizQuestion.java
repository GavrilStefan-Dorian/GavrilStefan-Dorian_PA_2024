package com.example.ProjectJava.entities;

import jakarta.persistence.*;

@Entity
@Table(name="quiz_questions", schema="public")
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quiz_question_id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Long getQuiz_question_id() {
        return quiz_question_id;
    }

    public void setQuiz_question_id(Long quiz_question_id) {
        this.quiz_question_id = quiz_question_id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    }