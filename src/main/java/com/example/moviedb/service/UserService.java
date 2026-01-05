package com.example.moviedb.service;

import com.example.moviedb.model.User;
import com.example.moviedb.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Сервис для пользователей
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Регистрация нового пользователя
    public User registerUser(String username, String rawPassword, String role) {
        // 1. Проверяем, не существует ли уже пользователь
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("User already exists: " + username);
        }

        // 2. Шифруем пароль
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 3. Определяем роль (если не передана → "USER")
        String userRole = (role != null && !role.trim().isEmpty()) ? role : "USER";

        // 4. Создаём и сохраняем пользователя
        User user = new User(username, encodedPassword, userRole);
        return userRepository.save(user);
    }
}