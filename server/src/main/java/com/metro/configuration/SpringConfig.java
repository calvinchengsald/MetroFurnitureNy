package com.metro.configuration;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.metro.controller.GeneralController;
import com.metro.model.ProductInfo;

@Configuration
@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.metro.*")
public class SpringConfig {

}
