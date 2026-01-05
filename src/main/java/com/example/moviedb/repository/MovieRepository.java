package com.example.moviedb.repository;

import com.example.moviedb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Репозиторий фильмов
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    //Кастомные методы
    Movie findByTitle(String title);
    List<Movie> findByDirector(String director);
    List<Movie> findByGenre(Movie.MovieGenres genre);
    List<Movie> findByReleaseYear(int releaseYear);


    @Query(value = "SELECT * FROM movie WHERE rating = (SELECT MAX(rating) FROM movie)",
            nativeQuery = true)
    List<Movie> findMoviesWithMaxRatingNative();
}