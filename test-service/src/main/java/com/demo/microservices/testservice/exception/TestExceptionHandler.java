package com.demo.microservices.testservice.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import com.demo.microservices.servicelibs.exception.ServiceExceptionHandler;
import com.demo.microservices.testservice.util.TestConstants;
import lombok.Generated;

/**
 * @author kaihe
 *
 */

@Generated
@ControllerAdvice
public class TestExceptionHandler extends ServiceExceptionHandler {

  @Override
  protected String getDefaultGeneralServerErrorID() {
    return TestConstants.DEFAULT_GENERAL_SERVCER_ERROR_ID;
  }

  @Override
  protected String getDefaultValidationErrorID() {
    return TestConstants.DEFAULT_VALIDATION_ERROR_ID;
  }

  @Override
  protected String getDefaultInvalidRequestErrorID() {
    return TestConstants.DEFAULT_INVALID_REQUEST_ERROR_ID;
  }

}
