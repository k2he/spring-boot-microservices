package com.demo.microservices.contactservice;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author kaihe
 *
 */

@SpringBootApplication(scanBasePackages = {"com.demo.microservices.contactservice", "com.demo.microservices.servicelibs"}) 
@EnableEurekaClient
@EnableCircuitBreaker // Enable circuit breakers
public class ContactServiceApplication {

  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  public static void main(String[] args) {
    SpringApplication.run(ContactServiceApplication.class, args);
  }

}
