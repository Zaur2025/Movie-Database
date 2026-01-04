package com.example.moviedb;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role = "USER"; // Роль по умолчанию

    // Пустой конструктор (обязательно для JPA)
    public User() {}

    // Конструктор для удобства
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // === ОБЯЗАТЕЛЬНЫЕ МЕТОДЫ UserDetails ===

    // 1. Возвращает список ролей пользователя (самое важное!)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
        // Spring Security ждёт роли в формате "ROLE_ИМЯ"
        // Если role = "USER", вернёт "ROLE_USER"
        // Если role = "ADMIN", вернёт "ROLE_ADMIN"
    }

    // 2. Геттер пароля
    @Override
    public String getPassword() {
        return password;
    }

    // 3. Геттер логина
    @Override
    public String getUsername() {
        return username;
    }

    // 4. Аккаунт не просрочен? (всегда true для простоты)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 5. Аккаунт не заблокирован? (всегда true)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 6. Креденшалы не просрочены? (всегда true)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 7. Аккаунт включён? (всегда true)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // === ОБЫЧНЫЕ ГЕТТЕРЫ И СЕТТЕРЫ ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}