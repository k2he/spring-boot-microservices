package com.demo.microservices.projectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.demo.microservices.servicelibs.config.AppProperties;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(AppProperties.class)
public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}

}

