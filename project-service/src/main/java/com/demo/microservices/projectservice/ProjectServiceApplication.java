package com.demo.microservices.projectservice;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"com.demo.microservices.projectservice", "com.demo.microservices.servicelibs"})
@EnableEurekaClient
@EnableCircuitBreaker // Enable circuit breakers
public class ProjectServiceApplication {

  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  public static void main(String[] args) {
    SpringApplication.run(ProjectServiceApplication.class, args);
  }

}
