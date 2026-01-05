package com.example.moviedb.controller;

import com.example.moviedb.model.Movie;
import com.example.moviedb.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// API для фильмов
@RestController
@RequestMapping("/api/movies")

public class MovieController {
    @Autowired
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

    @GetMapping("/by-director")
    List<Movie> getMovieByDirector(@RequestParam String director) {
        return movieService.getMoviesByDirector(director);
    }

    @GetMapping("/by-genre")
    List<Movie> getMoviesByGenre(@RequestParam Movie.MovieGenres genre) {
        return movieService.getMoviesByGenre(genre);
    }

    @GetMapping("/by-releaseyear")
    List<Movie> getMoviesByReleaseYear(@RequestParam int releaseyear) {
        return movieService.getMoviesByReleaseYear(releaseyear);
    }

    @GetMapping("/best")
    public List<Movie> getBestMovies() {
        return movieService.getBestMovies();
    }
}