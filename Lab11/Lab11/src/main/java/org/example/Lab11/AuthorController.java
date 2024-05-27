package org.example.Lab11;

import org.example.Lab11.entities.Author;
import org.example.Lab11.factories.AbstractFactory;
import org.example.Lab11.factories.JPAFactory;
import org.example.Lab11.repositories.AuthorRepositoryJPA;
import org.example.Lab11.repositories.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AbstractFactory factory = new JPAFactory();
    private final Repository<Author, Integer> authorRepo = factory.createAuthorRepository();
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
    public int createAuthor(@RequestParam String name) {
        int id = 1 + countAuthors();
        Author newAuthor = new Author(id, name);
        authorRepo.create(newAuthor);
        return id;
    }

    @PostMapping(value="/obj", consumes = "application/json")
    public ResponseEntity<String> createAuthor(@RequestBody Author author){
        authorRepo.create(author);
        return new ResponseEntity<>(
                "Author created successfully!",
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable int id, @RequestParam String name){
        Author author = authorRepo.findById(id);
        if(author == null) {
            return new ResponseEntity<>(
                    "Author not found",
                    HttpStatus.NOT_FOUND
            );
        }
        author.setName(name);
        return new ResponseEntity<>(
                "Author updated successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int id){
        Author author = authorRepo.findById(id);
        if(author == null)
            return new ResponseEntity<>(
                    "Author not found",
                    HttpStatus.GONE
            );
        authorRepo.delete(id);
        return new ResponseEntity<>(
                "Author deleted successfully!",
                HttpStatus.OK
        );
    }

}
