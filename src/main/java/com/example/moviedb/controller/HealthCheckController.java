package com.example.moviedb.controller;

import com.example.moviedb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/health")
    public String healthCheck() {
        long count = movieRepository.count(); //SELECT COUNT(*) FROM movie
        return "Database is working! Movies in DB: " + count;
    }
}