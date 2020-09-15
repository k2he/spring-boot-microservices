package com.demo.microservices.testservice.service;

import java.sql.SQLException;
import java.util.Date;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * @author kaihe
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  @NonNull
  private RestTemplate restTemplate;

  private static int RERTY_COUNTER = 0;

  private StringBuffer sb = new StringBuffer();
  
  @Override
  @Retryable(value = {RuntimeException.class, SQLException.class}, maxAttempts = 4,
      backoff = @Backoff(2000))
  public String testRetryFailed() throws SQLException, RuntimeException {
    RERTY_COUNTER++;
    
    String message = "testRetryFailed() Retry count = " + RERTY_COUNTER + " at " + new Date();
   
    sb.append(message);
    sb.append(System.lineSeparator());
    log.info(message);

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
    String error = "Total tried " + RERTY_COUNTER + " times --> recover() method called";
    log.info(error);
    sb.append(error);
    sb.append(System.lineSeparator());
    RERTY_COUNTER = 0;// Reset Retry Counter, it will be used for testRetrySuccess()
    
    String result = sb.toString();
    sb.setLength(0); // Clear all contents
    
    return result;
  }

  @Override
  @Retryable(value = {RuntimeException.class, SQLException.class}, maxAttempts = 4,
      backoff = @Backoff(2000))
  public String testRetrySuccess(String token) throws RuntimeException {
    RERTY_COUNTER++;
    
    String message = "testRetrySuccess() Retry count = " + RERTY_COUNTER + " on " + new Date();
    sb.append(message);
    sb.append(System.lineSeparator());
    log.info(message);

    if (RERTY_COUNTER < 3) {
      throw new RuntimeException();
    }

    sb.append("testRetrySuccess() try to get project (where projectid = 1) tried "
        + RERTY_COUNTER + " times." + " and result: \n Project = " + getProjectJson("1"));

    RERTY_COUNTER = 0; // Reset to default
    String result = sb.toString();
    sb.setLength(0);
    
    return result;
  }

  private String getProjectJson(String id) {
    Project project =
        restTemplate.getForObject("http://project-service/projects/" + id, Project.class);
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    try {
      return ow.writeValueAsString(project);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  @Override
  @HystrixCommand(commandKey = "test-circuit-breaker", fallbackMethod = "fallbackMethod")
  public String testCircuitBreaker() {
    String text = "testCircuitBreaker() called";
    sb.append(text);
    sb.append(System.lineSeparator());
    log.info(text);
    return getProjectJson("aaa");// Invalid project id
  }

  /* Potentially we can implement a caching, so if service is down, we can return from 
   * caching (eg: Redis, Pivotal Cloud Cache) or give an error.
   */
  public String fallbackMethod() {
    sb.append("testCircuitBreaker() failed -> fallbackMethod() called. Potentially in fallbackMethod() we can implement a caching, so if service is down, we can return ");
    sb.append(System.lineSeparator());
    sb.append("from caching (eg: Redis, Pivotal Cloud Cache) or give an error.");
    String result = sb.toString();
    sb.setLength(0);
    return result;
  }
}
