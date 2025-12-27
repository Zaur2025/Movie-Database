package com.example.moviedb;

import com.example.moviedb.exception.MovieNotFoundException;
import com.example.moviedb.exception.ValidationException;
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
        // Пример валидации
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            throw new ValidationException("Movie title cannot be empty");
        }
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