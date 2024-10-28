package com.ticgrp10.WTFCINEMA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ticgrp10.WTFCINEMA.Repositories")
public class WtfcinemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WtfcinemaApplication.class, args);
	}

}
