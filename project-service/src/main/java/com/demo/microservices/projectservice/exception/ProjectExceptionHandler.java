package com.demo.microservices.projectservice.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import com.demo.microservices.projectservice.util.ProjectConstants;
import com.demo.microservices.servicelibs.exception.ServiceExceptionHandler;
import lombok.Generated;

@Generated
@ControllerAdvice
public class ProjectExceptionHandler extends ServiceExceptionHandler {

  @Override
  protected String getDefaultGeneralServerErrorID() {
    return ProjectConstants.DEFAULT_GENERAL_SERVCER_ERROR_ID;
  }

  @Override
  protected String getDefaultValidationErrorID() {
    return ProjectConstants.DEFAULT_VALIDATION_ERROR_ID;
  }

  @Override
  protected String getDefaultInvalidRequestErrorID() {
    return ProjectConstants.DEFAULT_INVALID_REQUEST_ERROR_ID;
  }

}
