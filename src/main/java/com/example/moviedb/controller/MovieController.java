package com.example.moviedb.controller;

import com.example.moviedb.model.Movie;
import com.example.moviedb.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Фильмы", description = "API для управления фильмами в базе данных")

public class MovieController {
    @Autowired
    private MovieService movieService;

    // ---- GET /api/movies ----
    @Operation(summary = "Получить все фильмы",
            description = "Возвращает список всех фильмов, отсортированных по дате добавления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получен список фильмов",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    // ---- POST /api/movies ----
    @Operation(summary = "Создать новый фильм",
            description = "Добавляет фильм в базу данных. Все поля обязательны.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фильм успешно создан",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации (неверные данные)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public Movie createMovie(@Valid @RequestBody Movie newMovie) {
        return movieService.createMovie(newMovie);
    }

    // ---- GET /api/movies/{id} ----
    @Operation(summary = "Получить фильм по ID",
            description = "Возвращает фильм по его уникальному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фильм найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "404", description = "Фильм с таким ID не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    // ---- DELETE /api/movies/{id} ----
    @Operation(summary = "Удалить фильм по ID",
            description = "Удаляет фильм из базы данных по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фильм успешно удалён",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Фильм с таким ID не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {
        return movieService.deleteMovie(id);
    }

    // ---- GET /api/movies/by-title?title=... ----
    @Operation(summary = "Найти фильм по названию",
            description = "Ищет фильм по точному совпадению названия (регистрозависимо)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фильм найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "404", description = "Фильм с таким названием не найден")
    })
    @GetMapping("/by-title")
    Movie getMovieByTitle(@RequestParam String title) {
        return movieService.getMovieByTitle(title);
    }

    // ---- GET /api/movies/by-director?director=... ----
    @Operation(summary = "Найти фильмы по режиссёру",
            description = "Возвращает список фильмов, снятых указанным режиссёром")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список фильмов (может быть пустым)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)))
    })
    @GetMapping("/by-director")
    List<Movie> getMovieByDirector(@RequestParam String director) {
        return movieService.getMoviesByDirector(director);
    }

    @Operation(summary = "Найти фильмы по жанру",
            description = "Возвращает список фильмов по указанному жанру")
    @GetMapping("/by-genre")
    List<Movie> getMoviesByGenre(@RequestParam Movie.MovieGenres genre) {
        return movieService.getMoviesByGenre(genre);
    }

    @Operation(summary = "Найти фильмы по году",
            description = "Возвращает список фильмов, вышедших в указанном году")
    @GetMapping("/by-releaseyear")
    List<Movie> getMoviesByReleaseYear(@RequestParam int releaseyear) {
        return movieService.getMoviesByReleaseYear(releaseyear);
    }

    @Operation(summary = "Найти фильмы по наивысшему рейтингу",
            description = "Возвращает список фильмов с наивысшим рейтингом")
    @GetMapping("/best")
    public List<Movie> getBestMovies() {
        return movieService.getBestMovies();
    }
}