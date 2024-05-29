package org.example.Lab11.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class BooksClientService {
    final Logger log = LoggerFactory.getLogger(BooksClientService.class);
    final String uri = "https://localhost:443/books";


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
