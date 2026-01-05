package com.example.moviedb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Конфигурация безопасности
// 1. Аннотации — ВКЛЮЧАЕМ Spring Security
@Configuration          // "Это класс с настройками Spring"
@EnableWebSecurity      // "Включаем веб-безопасность" (магическая аннотация)
public class SecurityConfig {   // Имя может быть любым


    // 2. ГЛАВНЫЙ БИН — цепочка фильтров безопасности
    @Bean       // "Spring, создай этот объект и положи в контейнер"
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/hello", "/api/auth/**").permitAll()             // Разрешаем без аутентификации
                        .requestMatchers("/h2-console/**").permitAll()                          // Разрешаем без аутентификации
                        .requestMatchers("/admin/**").hasRole("ADMIN")                          // ← Только ADMIN
                        .requestMatchers("/api/movies/**").hasAnyRole("USER", "ADMIN") // USER или ADMIN
                        //http://localhost:8080/h2-console
                        .anyRequest().authenticated() // Всё остальное требует логина
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", false) // После логина переходим сюда, false = редиректить туда, куда хотел пользователь
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // После выхода переходим сюда
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())  // ← ВАЖНО для H2 Console!
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Шифрование паролей
    }
}