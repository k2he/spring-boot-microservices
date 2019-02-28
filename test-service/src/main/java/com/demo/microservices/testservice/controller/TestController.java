package com.demo.microservices.testservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.microservices.testservice.service.TestService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class TestController {

	@NonNull
	private TestService testService;
	
	// Test retry fail test case
	@GetMapping("/retry-failed")
	public String retryFailed() throws Exception {
		return testService.testRetryFailed();
	}
	
	// Test retry fail test case
	@GetMapping("/retry-success")
	public String retrySuccess(@RequestHeader("Authorization") String token) {
		return testService.testRetrySuccess(token);
	}

	// Test retry fail test case
	@GetMapping("/circuit-breaker")
	public String circuitBreaker() {
		return testService.testCircuitBreaker();
	}
}
