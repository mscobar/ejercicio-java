package com.ejercicio.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Manuel Escobar
 * @version 1.0.0
 * Java Main Application
 */
@SpringBootApplication
@ComponentScan
public class JavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaApplication.class, args);
	}

}
