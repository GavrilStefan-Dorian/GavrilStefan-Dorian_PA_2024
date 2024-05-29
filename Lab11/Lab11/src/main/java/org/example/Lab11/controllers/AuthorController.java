package org.example.Lab11.controllers;

import org.example.Lab11.entities.Author;
import org.example.Lab11.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private Repository<Author, Integer> authorRepo;

    @GetMapping
    public List<Author> getAuthors() {
        return authorRepo.findAll();
    }

    @GetMapping("/count")
    public int countAuthors() {
        return authorRepo.findAll().size();
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable("id") int id) {
        return authorRepo.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> createAuthor(@RequestParam String name) {
        Author newAuthor = new Author();
        newAuthor.setName(name);
        authorRepo.create(newAuthor);
        return new ResponseEntity<>("Author created successfully with id: " + newAuthor.getId(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/obj", consumes = "application/json")
    public ResponseEntity<String> createAuthor(@RequestBody Author author) {
        authorRepo.create(author);
        return new ResponseEntity<>("Author created successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable int id, @RequestParam String name) {
        Author author = authorRepo.findById(id);
        if (author == null) {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
        author.setName(name);
        authorRepo.update(author);
        return new ResponseEntity<>("Author updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int id) {
        Author author = authorRepo.findById(id);
        if (author == null) {
            return new ResponseEntity<>("Author not found", HttpStatus.GONE);
        }
        authorRepo.delete(id);
        return new ResponseEntity<>("Author deleted successfully!", HttpStatus.OK);
    }
}
