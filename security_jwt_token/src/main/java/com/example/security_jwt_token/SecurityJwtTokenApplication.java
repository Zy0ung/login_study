package com.example.security_jwt_token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SecurityJwtTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityJwtTokenApplication.class, args);
	}

}
