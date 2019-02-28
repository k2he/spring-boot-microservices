package com.demo.microservices.testservice.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Project {
	private Integer projectId;
	private String projectName;
	private String projectSummary;
	private String requiredSkills;
	private BigDecimal estimatedCost;
}
