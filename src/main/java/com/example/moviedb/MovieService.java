package com.example.moviedb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie createMovie(Movie movie) {
        //Бизнес-логика (проверки, преобразования)
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            throw new RuntimeException("Фильм с таким именем уже есть в базе!");
        }
        return movieRepository.save(movie);
    }

    public Movie getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public String deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return "Movie deleted successfully";
    }

    public Movie getMovieByTitle(@RequestParam String title) {
        return movieRepository.findByTitle(title);
    }

    public List<Movie> getMoviesByDirector(@RequestParam String director) {
        return movieRepository.findByDirector(director);
    }

    public List<Movie> getMoviesByGenre(@RequestParam Movie.MovieGenres genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getMoviesByReleaseYear(@RequestParam int releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear);
    }

    public List<Movie> getBestMovies() {
        return movieRepository.findMoviesWithMaxRatingNative();
    }
}