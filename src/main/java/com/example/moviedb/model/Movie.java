package com.example.moviedb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


// Сущность фильма
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    //@Size(min = 2, max = 25, message = "Название должно быть от 2 до 25 символов")
    private String title;

    @NotBlank(message = "Director is required")
    @Size(min = 2, max = 20, message = "Имя режиссера должно быть от 2 до 15 символов")
    private String director;

    private int releaseYear;

    @Enumerated(EnumType.STRING)
    private MovieGenres genre;

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Рейтинг не может быть меньше 0")
    @Max(value = 10, message = "Рейтинг не может быть больше 10")
    private int rating;

    // Пустой конструктор (обязательно!)
    Movie() {
    }

    // Конструктор (обязательно!)
    public Movie(String title, String director, int releaseYear, MovieGenres genre, int rating) {
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
        ФАНТАСТИКА, УЖАСЫ, КОМЕДИЯ, ПРИКЛЮЧЕНИЯ, ДЛЯ_ВЗРОСЛЫХ, ДОКУМЕНТАЛЬНЫЙ, ТРИЛЛЕР, ДРАМА
    }
}