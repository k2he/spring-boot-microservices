package com.demo.microservices.projectservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author kaihe
 *
 */

@Configuration
public class RestTemplateConfig {

  @Bean // Create a bean for restTemplate to call services
  @LoadBalanced // Load balance between service instances running at different ports.
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
