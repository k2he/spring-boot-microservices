package com.demo.microservices.authservice;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import com.demo.microservices.authservice.config.AppProperties;
/**
 * @author kaihe
 *
 */

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(AppProperties.class)
@EnableCircuitBreaker // Enable circuit breakers
public class AuthServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthServiceApplication.class, args);
  }

  @Bean
  public ConfigurableServletWebServerFactory webServerFactory() {
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
    factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
      @Override
      public void customize(Connector connector) {
        connector.setProperty("relaxedQueryChars", "|{}[]");
      }
    });
    return factory;
  }
}
