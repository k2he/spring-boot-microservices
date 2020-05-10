package com.demo.microservices.servicelibs.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ProjectNameValidator implements ConstraintValidator<ProjectName, String> {

    // You can also inject service and check if project name already exist in DB or not 
    
    public void initialize(ProjectName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      return !StringUtils.isBlank(value);
    }

}
