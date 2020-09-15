package com.demo.microservices.testservice.model;

import lombok.Builder;
import lombok.Data;
/**
 * @author kaihe
 *
 */

@Data
@Builder
public class TestResult {
  
  private String result;
  
}
