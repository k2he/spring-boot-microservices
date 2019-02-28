package com.demo.microservices.testservice.service;

import java.sql.SQLException;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface TestService {
	
	String testRetryFailed() throws SQLException, RuntimeException;
    String recover(Throwable t);
	
	String testRetrySuccess(String token) throws RuntimeException;
	
	String testCircuitBreaker();
}
