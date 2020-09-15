package com.demo.microservices.testservice.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/**
 * @author kaihe
 *
 */

@Data
@Builder
public class Project {
  
  private Integer projectId;
  
  private String projectName;
  
  private String projectSummary;
  
  private String requiredSkills;
  
  private BigDecimal estimatedCost;
  
}
