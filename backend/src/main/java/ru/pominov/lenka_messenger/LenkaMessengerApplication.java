package ru.pominov.lenka_messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LenkaMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LenkaMessengerApplication.class, args);
		System.out.println("Hello");
	}

}
