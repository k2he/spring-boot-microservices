package com.demo.microservices.gatewayservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "app")
@Getter
@Component
public class AppProperties {
	private final Oauth2 oauth2 = new Oauth2();
	
	@Getter
	@Setter
	public static class Oauth2 {
		private String authorizedRedirectUris;
	}
}
