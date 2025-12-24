package com.example.moviedb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloMovieController {
    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @GetMapping("/hello")
    public String anotherHello() {
        return "Another Hello from Spring Boot and Movie Database project!";
    }

    @GetMapping("/api/greet/{name}")
    public String greetByName(@PathVariable String name) {
        return "Hello " + name + "! Glad to see You!";
    }

    @GetMapping("/api/helloresponse/")
    public HelloResponse helloResponse() {
        return new HelloResponse("Hello!!!", System.currentTimeMillis());
    }

    static class HelloResponse {
        private String message;
        private Long time;

        HelloResponse(String message, Long time) {
            this.message = message;
            this.time = time;
        }

        //Геттеры (обязательно!)
        public String getMessage() {
            return message;
        }

        public Long getTime() {
            return time;
        }
    }
}