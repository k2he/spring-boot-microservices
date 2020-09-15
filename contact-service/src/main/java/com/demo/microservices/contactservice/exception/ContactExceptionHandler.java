package com.demo.microservices.contactservice.exception;

/**
 * @author kaihe
 *
 */


import org.springframework.web.bind.annotation.ControllerAdvice;
import com.demo.microservices.contactservice.util.ContactConstants;
import com.demo.microservices.servicelibs.exception.ServiceExceptionHandler;
import lombok.Generated;

@Generated
@ControllerAdvice
public class ContactExceptionHandler extends ServiceExceptionHandler {

  @Override
  protected String getDefaultGeneralServerErrorID() {
    return ContactConstants.DEFAULT_GENERAL_SERVCER_ERROR_ID;
  }

  @Override
  protected String getDefaultValidationErrorID() {
    return ContactConstants.DEFAULT_VALIDATION_ERROR_ID;
  }

  @Override
  protected String getDefaultInvalidRequestErrorID() {
    return ContactConstants.DEFAULT_INVALID_REQUEST_ERROR_ID;
  }

}
