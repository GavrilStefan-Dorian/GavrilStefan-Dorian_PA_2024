//package org.example.Lab11.services;
//
//import org.example.Lab11.entities.Author;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//
//import java.util.List;
//
//public class CallService {
//    final Logger log = LoggerFactory.getLogger(CallService.class);
//    final String uri = "http://localhost:8080/authors";
//
//    @GetMapping("/call")
//    public List<Author> getAuthors(){
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
//    @GetMapping(value = "/flux",
//            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Author> getProductsNonBlocking() {
//        log.info("Start");
//        Flux<Author> productFlux = WebClient.create()
//                .get()
//                .uri(uri)
//                .retrieve()
//                .bodyToFlux(Author.class);
//        productFlux.subscribe(p -> log.info(p.toString()));
//        log.info("Stop");
//        return productFlux;
//    }
//}
