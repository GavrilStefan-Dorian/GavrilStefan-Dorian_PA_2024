package org.example.Lab11.services;

import org.example.Lab11.entities.Author;
import org.example.Lab11.repositories.AuthorRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorsClientService {

    private final Logger log = LoggerFactory.getLogger(AuthorsClientService.class);

    @Autowired
    private AuthorRepositoryJPA authorRepository;

    public Flux<Author> getAllAuthors() {
        log.info("Fetching all authors asynchronously");
        return Flux.fromIterable(authorRepository.findAll());
    }

    public Mono<Integer> countAuthors() {
        log.info("Counting authors asynchronously");
        return Mono.just(authorRepository.findAll().size());
    }

    public Mono<Author> getAuthorById(int id) {
        log.info("Fetching author with id {} asynchronously", id);
        return Mono.justOrEmpty(authorRepository.findById(id));
    }

    public Mono<ResponseEntity<String>> createAuthor(String name) {
        log.info("Creating author {} asynchronously", name);
        Author author = new Author(name);
        authorRepository.create(author);
        return Mono.just(ResponseEntity.ok("Author created successfully"));
    }

    public Mono<ResponseEntity<String>> updateAuthor(int id, String name) {
        log.info("Updating author with id {}: new name - {} asynchronously", id, name);
        Author author = authorRepository.findById(id);
        if (author == null) {
            return Mono.just(ResponseEntity.notFound().build());
        }
        author.setName(name);
        authorRepository.update(author);
        return Mono.just(ResponseEntity.ok("Author updated successfully"));
    }

    public Mono<ResponseEntity<String>> deleteAuthor(int id) {
        log.info("Deleting author with id {} asynchronously", id);
        Author author = authorRepository.findById(id);
        if (author == null) {
            return Mono.just(ResponseEntity.notFound().build());
        }
        authorRepository.delete(id);
        return Mono.just(ResponseEntity.ok("Author deleted successfully"));
    }
}


//
//import org.example.Lab11.entities.Author;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//
//import java.util.List;
//
//@RestController
//
//public class AuthorsClientService {
//    final Logger log = LoggerFactory.getLogger(AuthorsClientService.class);
//    final String uri = "https://localhost:443/authors";
//
//    @GetMapping(value = "/call")
//    public List<Author> getAuthors() {
//        log.info("Start");
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<List<Author>> response = restTemplate.exchange(
//                uri, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Author>>() {
//                });
//        List<Author> result = response.getBody();
//        assert result != null;
//        result.forEach(p -> log.info(p.toString()));
//        log.info("Stop");
//        return result;
//    }
//
//    @GetMapping(value = "/flux",
//            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Author> getAuthorsNonBlocking() {
//        log.info("Start");
//        Flux<Author> authorFlux = WebClient.create()
//                .get()
//                .uri(uri)
//                .retrieve()
//                .bodyToFlux(Author.class)
//                .doOnNext(author -> log.info("Received author: {}", author))
//                .doOnError(error -> log.error("Error retrieving authors", error))
//                .doOnComplete(() -> log.info("Completed retrieving authors"));
//
//        authorFlux.subscribe(p -> log.info(p.toString()));
//        log.info("Stop");
//        return authorFlux;
//    }
//}
