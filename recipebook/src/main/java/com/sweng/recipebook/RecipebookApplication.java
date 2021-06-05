package com.sweng.recipebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RecipebookApplication - Entry point for REST service application.
 */
@SpringBootApplication
public class RecipebookApplication {

	/**
	 * main - Main method called to start Spring Boot application.
	 * 
	 * @param args - Line arguments passable to Spring Boot application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(RecipebookApplication.class, args);
	}

}
