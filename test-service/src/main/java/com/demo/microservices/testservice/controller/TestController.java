package com.demo.microservices.testservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.microservices.testservice.model.TestResult;
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
	@GetMapping("/retry-fail")
	public TestResult retryFailed() throws Exception {
		String result = testService.testRetryFailed();
		return new TestResult(result);
	}
	
	// Test retry fail test case
	@GetMapping("/retry-success")
	public TestResult retrySuccess(@RequestHeader("Authorization") String token) {
		String result = testService.testRetrySuccess(token);
		return new TestResult(result);
	}

	// Test retry fail test case
	@GetMapping("/circuit-breaker")
	public TestResult circuitBreaker() {
		String result = testService.testCircuitBreaker();
		return new TestResult(result);
	}
}
