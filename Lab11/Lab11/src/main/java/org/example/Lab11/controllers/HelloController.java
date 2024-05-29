package org.example.Lab11.controllers;

import org.example.Lab11.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;
    @RequestMapping("/hello")
    public String sayHello() {
        return helloService.sayHello("Spring Boot!");
    }
}
