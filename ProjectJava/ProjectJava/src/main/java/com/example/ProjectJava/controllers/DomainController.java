package com.example.ProjectJava.controllers;


import com.example.ProjectJava.entities.Domain;
import com.example.ProjectJava.entities.Question;
import com.example.ProjectJava.entities.UserResponse;
import com.example.ProjectJava.services.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/domains")
public class DomainController {

    @Autowired
    private DomainService domainService;

    // Get all domains
    @GetMapping
    public ResponseEntity<List<Domain>> getAllDomains() {
        List<Domain> domains = domainService.getAllDomains();
        return ResponseEntity.ok(domains);
    }

    // Get domain by ID
    @GetMapping("/{id}")
    public ResponseEntity<Domain> getDomainById(@PathVariable("id") Long domainId) {
        Optional<Domain> domain = domainService.getDomainById(domainId);
        return domain.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all questions
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = domainService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    // Get questions by domain ID
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getQuestionsByDomain(@PathVariable("id") Long domainId) {
        List<Question> questions = domainService.getQuestionsByDomain(domainId);
        return ResponseEntity.ok(questions);
    }

    // Get question by domain ID and question ID
    @GetMapping("/{domainId}/questions/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("domainId") Long domainId, @PathVariable("questionId") Long questionId) {
        Optional<Question> question = domainService.getQuestionById(domainId, questionId);
        return question.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get responses for a question in a domain
    @GetMapping("/{domainId}/questions/{questionId}/responses")
    public ResponseEntity<List<UserResponse>> getResponsesForQuestion(@PathVariable("domainId") Long domainId, @PathVariable("questionId") Long questionId) {
        List<UserResponse> responses = domainService.getResponsesForQuestion(domainId, questionId);
        return ResponseEntity.ok(responses);
    }
}


/*
    to create quiz, get questions of domain, associate to a new quiz id, add to quizzes table, add the quiz id to
     user history
    retrieve from history to display and show all questions -> select quiz, select questions

    /domains
    /domains/{id}
    /domains/{id}/questions
    /domains/{id}/quizzes
    /domains/{id}/questions/{id}
    /domains/{id}/quizzes/{id}



    cum iau quizurile?
    cum fac istoric?
    cum aleg domeniile?
    cum iau raspunsurile sa le validez, sau raspunsul ales
    cum iau intrebarile


    /users/{id}/quizzes/{id}/questions/{id}/responses

    userul incepe quiz

    get intrebare cu index random pe domeniu ales

    post quiz?domain=name -> metoda din quizservice sa faca get pe intrebari random din domeniul ales -> create quiz in bd
    -> trimite toate intrebarile la user
    adauga quizul in istoricul userului
    cand se termina jocul, submit la arrayul der aspunsuri prin POST users/{id}/quizzes/{id}/responses si valideaza prin get quizzes/{id}/answers
    pastreaza scorul in users/{id}/scores/{id} ->id_scor == id_quiz


    am nevoie de api/v1/domains -> lista de domenii din care alege sa fie dat quizul
    daca creeaza quizul, il adauga in tabela quizes prin POST api/v1/quizzes care primeste JSON quiz ce contine domeniul idul quizului si id-urile intrebarilor asociate


    get api/v1/domains -> json cu domenii pt a alege userul ce vrea sa fie domeniul quizului pe care il creeaza
    post api/v1/quizzes -> primeste json de clasa quiz ce contine id_quiz, domeniu, list<id_question>
    get api/v1/users/{id}/quizzes -> pt istoric, returneaza json cu lista clase quiz pt a fi afisate datele pe front
    get api/v1/users/{id}/quizzes/{id}/answers -> returneaza json raspunsurile la un quiz, pt a fi afisate la istoric
    how do i display the final score? do i get it with answers api ednpoint? or a new ednpoint for scores/{id}? please help
    is this enough for the app? user logs in ( or registers first if need to ) and it uses the api/v1/users/login api/v1/users/register. creates a quiz through the post, and gets a code for the quiz. he can wait for others to join by using the code, then when desird he starts the quiz game.
    get api/v1/

    post quiz
 */
