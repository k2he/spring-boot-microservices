package com.demo.microservices.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.demo.microservices.authservice.config.AppProperties;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(AppProperties.class)
@EnableCircuitBreaker      // Enable circuit breakers
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}

