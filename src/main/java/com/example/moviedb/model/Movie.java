package com.example.moviedb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Schema(description = "Сущность фильма")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор фильма", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotEmpty(message = "Title is required")
    @Size(min = 2, max = 25, message = "Название должно быть от 2 до 25 символов")
    @Schema(description = "Название фильма", example = "Матрица", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotEmpty(message = "Director is required")
    @Size(min = 2, max = 20, message = "Имя режиссера должно быть от 2 до 15 символов")
    @Schema(description = "Имя режиссёра", example = "Кристофер Нолан", requiredMode = Schema.RequiredMode.REQUIRED)
    private String director;

    @Schema(description = "Год выпуска", example = "1999", requiredMode = Schema.RequiredMode.REQUIRED)
    private int releaseYear;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Жанр фильма",
            example = "ФАНТАСТИКА")
    private MovieGenres genre;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0", message = "Рейтинг не может быть меньше 0")
    @DecimalMax(value = "10", message = "Рейтинг не может быть больше 10")
    @Schema(description = "Рейтинг фильма от 0 до 10", example = "8", requiredMode = Schema.RequiredMode.REQUIRED)
    private int rating;

    // Пустой конструктор (обязательно!)
    Movie() {
    }

    // Конструктор (обязательно!)
    Movie(String title, String director, int releaseYear, MovieGenres genre, int rating) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
    }

    // Геттеры и сеттеры (обязательно!)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public MovieGenres getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setGenre(MovieGenres genre) {
        this.genre = genre;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public enum MovieGenres {
        ФАНТАСТИКА, УЖАСЫ, КОМЕДИЯ, ПРИКЛЮЧЕНИЯ, ДЛЯ_ВЗРОСЛЫХ, ДОКУМЕНТАЛЬНЫЙ, ТРИЛЛЕР
    }
}