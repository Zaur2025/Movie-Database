package com.example.moviedb.service;

import com.example.moviedb.model.Movie;
import com.example.moviedb.repository.MovieRepository;
import com.example.moviedb.exception.MovieNotFoundException;
import com.example.moviedb.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


// Сервис для фильмов
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie createMovie(Movie movie) {
        // 1. Бизнес-правила (проверка дубликатов, лимитов и т.д.)
        //checkBusinessRules(movie);
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            throw new RuntimeException("Фильм с таким именем уже есть в базе!");
        }

        // 2. Операция с БД
        return movieRepository.save(movie);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public String deleteMovie(Long id) {
        movieRepository.deleteById(id);
        return "Movie deleted successfully";
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public List<Movie> getMoviesByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    public List<Movie> getMoviesByGenre(Movie.MovieGenres genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getMoviesByReleaseYear(int releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear);
    }

    public List<Movie> getBestMovies() {
        return movieRepository.findMoviesWithMaxRatingNative();
    }
}