package com.example.moviedb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Spring сам создаст метод!
    boolean existsByUsername(String username); // Проверка существования пользователя
}