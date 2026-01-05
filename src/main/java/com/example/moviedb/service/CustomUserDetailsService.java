package com.example.moviedb.service;

import com.example.moviedb.repository.UserRepository;
import com.example.moviedb.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// Сервис для Security
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Конструктор с инъекцией зависимости (лучше чем @Autowired)
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Ищем пользователя в БД по имени
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Возвращаем найденного пользователя
        // Важно: User уже реализует UserDetails, поэтому просто возвращаем его
        return user;
    }
}