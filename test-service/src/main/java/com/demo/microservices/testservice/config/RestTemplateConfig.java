package com.demo.microservices.testservice.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
/**
 * @author kaihe
 *
 */

@Configuration
public class RestTemplateConfig {

  // Create a bean for restTemplate to call services
  @Bean
  @LoadBalanced // Load balance between service instances running at different ports.
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }
    interceptors.add(new RestTemplateInterceptor());
    restTemplate.setInterceptors(interceptors);
    return restTemplate;
  }
}
