package com.bookonthego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		// Set them as system properties so Spring Boot can read them

		SpringApplication.run(DemoApplication.class, args);

	}

}
