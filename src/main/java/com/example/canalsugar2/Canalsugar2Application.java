package com.example.canalsugar2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Canalsugar2Application {

	public static void main(String[] args) {
		SpringApplication.run(Canalsugar2Application.class, args);
	}

}
