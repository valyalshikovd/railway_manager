package com.example.railway_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class RailwayManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayManagerApplication.class, args);
	}

}
