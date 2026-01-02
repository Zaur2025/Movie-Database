package com.example.moviedb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/hello", "/api/auth/**").permitAll() // Разрешаем без аутентификации
                        .anyRequest().authenticated() // Всё остальное требует логина
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/api/movies", true) // После логина переходим сюда
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // После выхода переходим сюда
                )
                .csrf().disable(); // Отключаем CSRF для API (для упрощения)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Шифрование паролей
    }
}