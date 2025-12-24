package com.example.moviedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviedbApplication {

	public static void main(String[] args) {
		System.out.println("Hello!");
		SpringApplication.run(MoviedbApplication.class, args);
		System.out.println("Spring Boot is started!");
	}
}