package com.gebeya.pro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/*

@name: Alemayehu Mekonen
@email: alemayehu.dev@gmail.com

 */

@RestController
@SpringBootApplication
@ComponentScan("com.gebeya.pro")
public class ProApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProApplication.class, args);
	}

}
