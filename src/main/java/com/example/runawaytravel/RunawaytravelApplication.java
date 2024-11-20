package com.example.runawaytravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.runawaytravel.repository"})
@EntityScan(basePackages = {"com.example.runawaytravel.entity"})
public class RunawaytravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunawaytravelApplication.class, args);
	}

}
