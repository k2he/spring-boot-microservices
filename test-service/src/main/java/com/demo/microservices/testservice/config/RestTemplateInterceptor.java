package com.demo.microservices.testservice.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		String token = RequestContext.getContext().getToken();
		// request.getHeaders().add(JwtConstants.HEADER_STRING, token);

		request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

		return execution.execute(request, body);
	}
}
