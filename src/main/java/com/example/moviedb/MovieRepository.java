package com.example.moviedb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    //Кастомные методы
    Movie findByTitle(String title);

    @Query(value = "SELECT * FROM movie WHERE rating = (SELECT MAX(rating) FROM movie)",
            nativeQuery = true)
    List<Movie> findMoviesWithMaxRatingNative();
}