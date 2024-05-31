package org.example.Lab11.services;

import org.example.Lab11.entities.Author;
import org.example.Lab11.entities.Book;
import org.example.Lab11.entities.Genre;
import org.example.Lab11.repositories.AuthorRepositoryJPA;
import org.example.Lab11.repositories.BookRepositoryJPA;
import org.example.Lab11.repositories.GenreRepositoryJPA;
import org.graph4j.Digraph;
import org.graph4j.Edge;
import org.graph4j.GraphBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class BooksClientService {

    private final Logger log = LoggerFactory.getLogger(BooksClientService.class);

    @Autowired
    private BookRepositoryJPA bookRepository;

    @Autowired
    private GenreRepositoryJPA genreRepository;

    @Autowired
    private AuthorRepositoryJPA authorRepository;

    public Flux<Book> getLongestSequence() {
        Random random = new Random();
        Digraph g = GraphBuilder.empty().buildDigraph();

        List<Book> books = bookRepository.findAll();
        int size = books.size();

        IntStream.range(0, size).forEach(g::addVertex);

        IntStream.range(0, size).forEach(i ->
                IntStream.range(i + 1, size).forEach(j -> {
                    if (random.nextBoolean())
                        g.addEdge(i, j);
                    else
                        g.addEdge(j, i);
                }));

        List<List<Book>> sequences = new ArrayList<>(Collections.nCopies(size, new ArrayList<>()));

        for (int vertex : g.vertices()) {
            Edge[] outgoingEdges = g.outgoingEdgesFrom(vertex);
            for (var edge : outgoingEdges) {
                Book sourceBook = books.get(edge.source());
                Book targetBook = books.get(edge.target());
                if (sourceBook.getPublicationDate().before(targetBook.getPublicationDate())) {
                    if (sequences.get(edge.target()).size() < sequences.get(vertex).size() + 1) {
                        sequences.set(edge.target(), new ArrayList<>(sequences.get(vertex)));
                        sequences.get(edge.target()).add(books.get(vertex));
                    }
                }
            }
        }

        List<Book> longestSequence = sequences.stream()
                .max(Comparator.comparingInt(List::size))
                .orElse(new ArrayList<>());

        return Flux.fromIterable(longestSequence);
    }


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
        bookRepository.save(book);
        return Mono.just(ResponseEntity.ok("Book created successfully"));
    }

    public Mono<ResponseEntity<String>> createBook(Book book) {
        log.info("Creating book {} asynchronously", book);
        bookRepository.save(book);
        return Mono.just(ResponseEntity.ok("Book created successfully"));
    }

    public Mono<ResponseEntity<String>> updateBook(int id, Book updatedBook) {
        log.info("Updating book with id {}: new details - {} asynchronously", id, updatedBook);
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) {
            return Mono.just(ResponseEntity.notFound().build());
        }

        Genre existingGenre = genreRepository.findByName(updatedBook.getGenre().getName());
        if (existingGenre == null) {
            Genre newGenre = new Genre(updatedBook.getGenre().getName());
            genreRepository.save(newGenre);
            updatedBook.setGenre(newGenre);
        } else {
            updatedBook.setGenre(existingGenre);
        }

        List<Author> updatedAuthors = new ArrayList<>();
        for (Author author : updatedBook.getAuthors()) {
            Author existingAuthor = authorRepository.findByName(author.getName());
            if (existingAuthor == null) {
                authorRepository.save(author);
                updatedAuthors.add(author);
            } else {
                updatedAuthors.add(existingAuthor);
            }
        }
        updatedBook.setAuthors(updatedAuthors);

        existingBook.get().setTitle(updatedBook.getTitle());
        existingBook.get().setLanguage(updatedBook.getLanguage());
        existingBook.get().setAuthors(updatedBook.getAuthors());
        existingBook.get().setNumberOfPages(updatedBook.getNumberOfPages());
        existingBook.get().setPublicationDate(updatedBook.getPublicationDate());

        bookRepository.save(existingBook.get());
        return Mono.just(ResponseEntity.ok("Book updated successfully"));
    }

    public Mono<ResponseEntity<String>> deleteBook(int id) {
        log.info("Deleting book with id {} asynchronously", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return Mono.just(ResponseEntity.notFound().build());
        }
        bookRepository.delete(book.get());
        return Mono.just(ResponseEntity.ok("Book deleted successfully"));
    }
}
