package org.example.Lab11.services;

import org.example.Lab11.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController

public class BooksClientService {
    final Logger log = LoggerFactory.getLogger(BooksClientService.class);
    final String uri = "http://localhost:8081/books";


//    @GetMapping(value = "/call")
//    public List<Book> getBooks() {
//        log.info("Start");
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<List<Book>> response = restTemplate.exchange(
//                uri, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Book>>() {
//                });
//        List<Book> result = response.getBody();
//        assert result != null;
//        result.forEach(p -> log.info(p.toString()));
//        log.info("Stop");
//        return result;
//    }
//
//    @GetMapping(value = "/flux",
//            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Book> getBooksNonBlocking() {
//        log.info("Start");
//        Flux<Book> bookFlux = WebClient.create()
//                .get()
//                .uri(uri)
//                .retrieve()
//                .bodyToFlux(Book.class)
//                .doOnNext(book -> log.info("Received author: {}", book))
//                .doOnError(error -> log.error("Error retrieving authors", error))
//                .doOnComplete(() -> log.info("Completed retrieving authors"));
//
//        bookFlux.subscribe(p -> log.info(p.toString()));
//        log.info("Stop");
//        return bookFlux;
//    }
}
