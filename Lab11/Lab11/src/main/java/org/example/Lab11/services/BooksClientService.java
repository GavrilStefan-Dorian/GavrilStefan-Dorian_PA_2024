package org.example.Lab11.services;

import org.example.Lab11.entities.Author;
import org.example.Lab11.entities.Book;
import org.example.Lab11.entities.Genre;
import org.example.Lab11.repositories.AuthorRepositoryJPA;
import org.example.Lab11.repositories.BookRepositoryJPA;
import org.example.Lab11.repositories.GenreRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksClientService {

    private final Logger log = LoggerFactory.getLogger(BooksClientService.class);

    @Autowired
    private BookRepositoryJPA bookRepository;

    @Autowired
    private GenreRepositoryJPA genreRepository;
    @Autowired
    private AuthorRepositoryJPA authorRepository;

    public Flux<Book> getAllBooks() {
        log.info("Fetching all books asynchronously");
        return Flux.fromIterable(bookRepository.findAll());
    }

    public Mono<Integer> countBooks() {
        log.info("Counting books asynchronously");
        return Mono.just(bookRepository.findAll().size());
    }

    public Mono<Book> getBookById(int id) {
        log.info("Fetching book with id {} asynchronously", id);
        return Mono.justOrEmpty(bookRepository.findById(id));
    }

    public Mono<ResponseEntity<String>> createBook(String title) {
        log.info("Creating book {} asynchronously", title);
        Book book = new Book();
        book.setTitle(title);
        bookRepository.create(book);
        return Mono.just(ResponseEntity.ok("Book created successfully"));
    }

    public Mono<ResponseEntity<String>> createBook(Book book) {
        log.info("Creating book {} asynchronously", book);
        bookRepository.create(book);
        return Mono.just(ResponseEntity.ok("Book created successfully"));
    }

    public Mono<ResponseEntity<String>> updateBook(int id, Book updatedBook) {
        log.info("Updating book with id {}: new details - {} asynchronously", id, updatedBook);
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            return Mono.just(ResponseEntity.notFound().build());
        }

        // Check if the genre of the updated book exists in the database
        Genre existingGenre = genreRepository.findByName(updatedBook.getGenreName().getName());
        if (existingGenre == null) {
            // If the genre doesn't exist, create it
            Genre newGenre = new Genre(updatedBook.getGenreName().getName());
            genreRepository.create(newGenre);
            updatedBook.setGenreName(newGenre);
        } else {
            // If the genre exists, set it in the updated book
            updatedBook.setGenreName(existingGenre);
        }

        // Check and save any new authors associated with the book
        List<Author> updatedAuthors = new ArrayList<>();
        for (Author author : updatedBook.getAuthors()) {
            Author existingAuthor = authorRepository.findByName(author.getName());
            if (existingAuthor == null) {
                // If the author doesn't exist, create it
                authorRepository.create(author);
                updatedAuthors.add(author);
            } else {
                updatedAuthors.add(existingAuthor);
            }
        }
        updatedBook.setAuthors(updatedAuthors);

        // Update the existing book with the updated details
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setLanguage(updatedBook.getLanguage());
        existingBook.setAuthors(updatedBook.getAuthors());
        existingBook.setNumberOfPages(updatedBook.getNumberOfPages());
        existingBook.setPublicationDate(updatedBook.getPublicationDate());

        bookRepository.update(existingBook);
        return Mono.just(ResponseEntity.ok("Book updated successfully"));
    }



    public Mono<ResponseEntity<String>> deleteBook(int id) {
        log.info("Deleting book with id {} asynchronously", id);
        Book book = bookRepository.findById(id);
        if (book == null) {
            return Mono.just(ResponseEntity.notFound().build());
        }
        bookRepository.delete(id);
        return Mono.just(ResponseEntity.ok("Book deleted successfully"));
    }
}
