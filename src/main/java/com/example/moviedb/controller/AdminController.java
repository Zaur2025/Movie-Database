package com.example.moviedb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Админ-функции
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Admin dashboard - доступ только для ADMIN!";
    }

    @GetMapping("/users")
    public String manageUsers() {
        return "Управление пользователями";
    }
}