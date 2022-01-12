package com.alkemy.challenge;

import com.alkemy.challenge.entity.Role;
import com.alkemy.challenge.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

		@Bean
		CommandLineRunner run(AppUserService userService) {
		return args -> {
			if(userService.findAllRoles().isEmpty()){
				userService.saveRole(new Role(null, "ADMIN"));
				userService.saveRole(new Role(null, "USER"));
			}
		};

	}

}
