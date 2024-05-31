package org.example.Lab11.controllers;

import org.example.Lab11.entities.Author;
import org.example.Lab11.entities.Book;
import org.example.Lab11.services.BooksClientService;
import org.graph4j.Digraph;
import org.graph4j.GraphBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BooksClientService booksService;
    @PreAuthorize("hasRole('client')")
    @GetMapping("/sequence")
    public Flux<Book> getLongestSequence(){
        return booksService.getLongestSequence();
    }

    @GetMapping
    public Flux<Book> getBooks() {
        return booksService.getAllBooks();
    }

    @GetMapping("/count")
    public Mono<Integer> countBooks() {
        return booksService.countBooks();
    }

    @GetMapping("/{id}")
    public Mono<Book> getBook(@PathVariable int id) {
        return booksService.getBookById(id);
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createBook(@RequestParam String title) {
        return booksService.createBook(title);
    }

    @PostMapping(value = "/obj", consumes = "application/json")
    public Mono<ResponseEntity<String>> createBook(@RequestBody Book book) {
        return booksService.createBook(book);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        return booksService.updateBook(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteBook(@PathVariable int id) {
        return booksService.deleteBook(id);
    }
}


//package org.example.Lab11.controllers;
//
//import org.example.Lab11.entities.Author;
//import org.example.Lab11.entities.Book;
//import org.example.Lab11.entities.Genre;
//import org.example.Lab11.repositories.Repository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/books")
//public class BookController {
//
//    @Autowired
//    private Repository<Book, Integer> bookRepo;
//
//    @Autowired
//    private Repository<Author, Integer> authorRepo;
//
//    @Autowired
//    private Repository<Genre, Integer> genreRepo;
//
//    @GetMapping
//    public List<Book> getBooks() {
//        return bookRepo.findAll();
//    }
//
//    @GetMapping("/count")
//    public int countBooks() {
//        return bookRepo.findAll().size();
//    }
//
//    @GetMapping("/{id}")
//    public Book getBook(@PathVariable("id") int id) {
//        return bookRepo.findById(id);
//    }
//
//    @PostMapping
//    public ResponseEntity<String> createBook(@RequestParam String title) {
//        Book newBook = new Book();
//        newBook.setTitle(title);
//        bookRepo.create(newBook);
//        return new ResponseEntity<>("Book created with success with id: " + newBook.getId(), HttpStatus.CREATED);
//    }
//
//    @PostMapping(value = "/obj", consumes = "application/json")
//    public ResponseEntity<String> createBook(@RequestBody Book book) {
//        bookRepo.create(book);
//        return new ResponseEntity<>("Book created successfully!", HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
//        Book existingBook = bookRepo.findById(id);
//        if (existingBook == null) {
//            return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
//        }
//
//        if (updatedBook.getTitle() != null && !updatedBook.getTitle().isEmpty()) {
//            existingBook.setTitle(updatedBook.getTitle());
//        }
//        if (updatedBook.getLanguage() != null) {
//            existingBook.setLanguage(updatedBook.getLanguage());
//        }
//        if (updatedBook.getAuthors() != null) {
//            List<Author> authors = updatedBook.getAuthors().stream()
//                    .map(author -> authorRepo.findByName(author.getName()))
//                    .collect(Collectors.toList());
//            existingBook.setAuthors(authors);
//        }
//        if (updatedBook.getGenreName() != null) {
//            Genre genre = genreRepo.findByName(updatedBook.getGenreName().getName());
//            existingBook.setGenreName(genre);
//        }
//        if (updatedBook.getNumberOfPages() != 0) {
//            existingBook.setNumberOfPages(updatedBook.getNumberOfPages());
//        }
//        if (updatedBook.getPublicationDate() != null) {
//            existingBook.setPublicationDate(updatedBook.getPublicationDate());
//        }
//
//        bookRepo.update(existingBook);
//        return new ResponseEntity<>("Book updated successfully!", HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteBook(@PathVariable int id) {
//        Book book = bookRepo.findById(id);
//        if (book == null) {
//            return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
//        }
//        bookRepo.delete(id);
//        return new ResponseEntity<>("Book was deleted!", HttpStatus.OK);
//    }
//}
