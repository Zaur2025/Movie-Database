package com.example.moviedb;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")

public class MovieController {
    @Autowired
    //private MovieRepository movieRepository;
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    public Movie createMovie(@Valid @RequestBody Movie newMovie) {
        return movieService.createMovie(newMovie);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {
        return movieService.deleteMovie(id);
    }

    @GetMapping("/by-title")
    Movie getMovieByTitle(@RequestParam String title) {
        return movieService.getMovieByTitle(title);
    }

    @GetMapping("/best")
    public List<Movie> getBestMovies() {
        return movieService.getBestMovies();
    }
}