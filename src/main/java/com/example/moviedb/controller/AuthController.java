package com.example.moviedb.controller;

import com.example.moviedb.model.User;
import com.example.moviedb.model.dto.RegisterRequest;
import com.example.moviedb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Регистрация/аутентификация
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    // Конструктор вместо Autowired, но лучше
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) { // DTO!
        try {
            User user = userService.registerUser(
                    request.getUsername(),
                    request.getPassword(),
                    request.getRole() // ← Передаём роль (может быть null)
            ); // делегируем
            return ResponseEntity.ok("User registered: " + user.getUsername() + ", role: " + user.getRole());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}