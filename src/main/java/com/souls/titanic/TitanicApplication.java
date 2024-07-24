package com.souls.titanic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TitanicApplication {

	public static void main(String[] args) {
		SpringApplication.run(TitanicApplication.class, args);
	}

}
