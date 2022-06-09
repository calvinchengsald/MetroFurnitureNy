package com.metro.server;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.metro.controller.GeneralController;

@SpringBootApplication
@ComponentScan(basePackages = "com.metro.*")
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		
	}

}

