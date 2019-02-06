package com.demo.microservices.contactservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.demo.microservices.servicelibs.config.AppProperties;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(AppProperties.class)
public class ContactServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactServiceApplication.class, args);
	}

}

