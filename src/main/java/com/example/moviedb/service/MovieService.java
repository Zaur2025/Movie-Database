package com.example.moviedb.service;

import com.example.moviedb.model.Movie;
import com.example.moviedb.repository.MovieRepository;
import com.example.moviedb.exception.MovieNotFoundException;
import com.example.moviedb.exception.ValidationException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


// Сервис для фильмов
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired  // ← ДОБАВЛЯЕМ RabbitTemplate
    private RabbitTemplate rabbitTemplate;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie createMovie(Movie movie) {
        // 1. Бизнес-правила (проверка дубликатов, лимитов и т.д.)
        //checkBusinessRules(movie);
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            throw new RuntimeException("Фильм с таким именем уже есть в базе!");
        }

        // Сохраняем фильм
        Movie savedMovie = movieRepository.save(movie);
        // ОТПРАВЛЯЕМ СООБЩЕНИЕ В RABBITMQ (асинхронно)
        rabbitTemplate.convertAndSend(
                "",  // exchange (пусто = default exchange)
                "movie.created.queue",  // имя очереди
                "Создан фильм: " + savedMovie.getTitle() + " (ID: " + savedMovie.getId() + ")"
        );

        System.out.println("✅ Фильм сохранён и сообщение отправлено в RabbitMQ");
        return savedMovie;
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public String deleteMovie(Long id) {
        // Проверяем существование фильма
        if (!movieRepository.existsById(id)) {
            throw new MovieNotFoundException(id);
        }
        // Удаляем фильм
        movieRepository.deleteById(id);

        // ОТПРАВЛЯЕМ СООБЩЕНИЕ В RABBITMQ (асинхронно)
        rabbitTemplate.convertAndSend(
                "",
                "movie.deleted.queue",
                "Удалён фильм с ID: " + id
        );

        System.out.println("✅ Фильм удалён и сообщение отправлено в RabbitMQ");
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