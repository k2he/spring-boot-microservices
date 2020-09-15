package com.demo.microservices.gatewayservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author kaihe
 *
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // TODO: in real project, should allow certain port/url, not to all.
    registry.addMapping("/**").allowedMethods("*");
  }
}
