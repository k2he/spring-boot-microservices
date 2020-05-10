package com.demo.microservices.servicelibs.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import com.demo.microservices.servicelibs.util.CommonConstants;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = ProjectNameValidator.class)
public @interface ProjectName {

    String message() default CommonConstants.PROJECT_ID_VALIDATION_MESSAGE;

    Class<?>[] groups() default { };

    Class<?>[] payload() default { };
}
