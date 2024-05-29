package org.example.Lab11.controllers;

import org.example.Lab11.entities.Author;
import org.example.Lab11.services.AuthorsClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorsClientService authorService;

    @GetMapping
    public Flux<Author> getAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/count")
    public Mono<Integer> countAuthors() {
        return authorService.countAuthors();
    }

    @GetMapping("/{id}")
    public Mono<Author> getAuthor(@PathVariable int id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createAuthor(@RequestParam String name) {
        return authorService.createAuthor(name);
    }

    @PostMapping(value = "/obj", consumes = "application/json")
    public Mono<ResponseEntity<String>> createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author.getName());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateAuthor(@PathVariable int id, @RequestParam String name) {
        return authorService.updateAuthor(id, name);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteAuthor(@PathVariable int id) {
        return authorService.deleteAuthor(id);
    }
}
