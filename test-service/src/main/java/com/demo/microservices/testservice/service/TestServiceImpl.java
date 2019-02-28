package com.demo.microservices.testservice.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.microservices.testservice.model.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

	@NonNull
	private RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
	
	private static int RERTY_COUNTER = 0;
	
	@Override
	@Retryable(value = {RuntimeException.class, SQLException.class},
    maxAttempts = 4, backoff = @Backoff(2000))
	public String testRetryFailed() throws SQLException, RuntimeException {
		RERTY_COUNTER++;
		logger.info("testRetryFailed() Retry count = " + RERTY_COUNTER);
        
		if (RERTY_COUNTER == 1) {
			throw new RuntimeException();
		} else if (RERTY_COUNTER == 2) {
			throw new SQLException();
		} else {
			throw new NullPointerException();
		}
	}
	
	@Override
	@Recover
    public String recover(Throwable t) {
		String error = "Total tried " +  RERTY_COUNTER + " times --> recover() method called";
		logger.info(error);
		RERTY_COUNTER = 0;//Reset Retry Counter, it will be used for testRetrySuccess()
        return error;
    }

	@Override
	@Retryable(value = {RuntimeException.class, SQLException.class},
    maxAttempts = 4, backoff = @Backoff(2000))
	public String testRetrySuccess(String token) throws RuntimeException {
		RERTY_COUNTER++;
		logger.info("testRetrySuccess() Retry count = " + RERTY_COUNTER);
		
		if (RERTY_COUNTER < 4) {
			throw new RuntimeException();
		}
		
		String message = "testRetrySuccess() try to get project (where projectid = 1) tried " +  RERTY_COUNTER + " times." 
				+ " and result: \n Project = " + getProjectJson("1");
		
		RERTY_COUNTER = 0; //Reset to default
		
		return message;
	}
	
	private String getProjectJson(String id) {
		Project project = restTemplate.getForObject("http://project-service/projects/" + id, Project.class);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			return ow.writeValueAsString(project);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	@Override
	@HystrixCommand(commandKey = "test-circuit-breaker", fallbackMethod="circuitBreakerDefault")
	public String testCircuitBreaker() {
		logger.info("testCircuitBreaker() called");
		return getProjectJson("aaa");//Invalid project id
	}
	
	public String circuitBreakerDefault() {
		return "testCircuitBreaker() failed -> circuitBreakerDefault() called";
	}
}
