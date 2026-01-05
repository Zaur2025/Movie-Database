package com.example.moviedb.model.dto;

// DTO (Data Transfer Objects) для регистрации
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // Может быть null или пустым
    //private String role = "USER"; // Значение по умолчанию

    // Пустой конструктор
    public RegisterRequest() {
    }

    // Геттеры и сеттеры (обязательно!)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
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