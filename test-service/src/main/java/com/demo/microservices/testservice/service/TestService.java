package com.demo.microservices.testservice.service;

import java.sql.SQLException;
/**
 * @author kaihe
 *
 */

public interface TestService {

  String testRetryFailed() throws SQLException, RuntimeException;

  String recover(Throwable t);

  String testRetrySuccess(String token) throws RuntimeException;

  String testCircuitBreaker();
}
