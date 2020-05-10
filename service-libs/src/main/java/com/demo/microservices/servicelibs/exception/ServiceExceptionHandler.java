package com.demo.microservices.servicelibs.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.demo.microservices.servicelibs.dto.AdditionalStatus;
import com.demo.microservices.servicelibs.dto.ErrorRestResponse;
import com.demo.microservices.servicelibs.dto.Status;
import lombok.extern.slf4j.Slf4j;
import com.demo.microservices.servicelibs.util.CommonConstants;

/*
 * @author hekai27
 * 
 * The Http Statuses to be used in response to external components, i.e. UI: SystemException (500,
 * "Internal Server Error") ApplicationException (400, "Bad Request") --> Business Exception, such
 * as searching for project, found a match but expired. ResourceNotFoundException (404,
 * "Not Found") InvalidRequestException (500, "Internal Server Error") ValidationException (400,
 * "Bad Request")
 * 
 * Extends this class in your component and add annotation @ControllerAdvice
 */
// @ControllerAdvice
@Slf4j
public abstract class ServiceExceptionHandler {

  protected abstract String getDefaultGeneralServerErrorID();

  protected abstract String getDefaultValidationErrorID();

  protected abstract String getDefaultInvalidRequestErrorID();

  /*
   * ======================= Spring boot auto generated Exceptions
   * =============================================
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected ErrorRestResponse handleMethodArgumentNotValidException(
      HttpServletResponse httpServletResponse, MethodArgumentNotValidException ex) {
    log.error(ex.getMessage(), ex);

    // Get all errors and convert to additionalStatus list
    List<AdditionalStatus> additionalStatusList = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> toAdditionalStatus(error)).collect(Collectors.toList());

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected ErrorRestResponse handleConstraintViolationException(
      HttpServletResponse httpServletResponse, ConstraintViolationException ex) {
    log.error(ex.getMessage(), ex);

    // Get all errors and convert to additionalStatus list
    List<AdditionalStatus> additionalStatusList = ex.getConstraintViolations().stream()
        .map(error -> toAdditionalStatus(error)).collect(Collectors.toList());

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }

  // Handle Invalid Request
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class,
      MissingServletRequestParameterException.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  protected ErrorRestResponse handleHttpRequestMethodNotSupportedException(
      HttpServletResponse httpServletResponse, Exception ex) {
    log.error(ex.getMessage(), ex);

    List<AdditionalStatus> additionalStatusList = new ArrayList<AdditionalStatus>();
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(getDefaultGeneralServerErrorID())
            .severity(CommonConstants.INTERNAL_SERVER_ERROR_SEVERITY)
            .statusDesc(CommonConstants.INTERNAL_SERVER_ERROR_DESCRIPTION).build();
    additionalStatusList.add(additionalStatus);

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }

  // Default error handling
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  protected ErrorRestResponse handleDefaultException(HttpServletResponse httpServletResponse,
      Exception ex) {
    log.error(ex.getMessage(), ex);

    List<AdditionalStatus> additionalStatusList = new ArrayList<AdditionalStatus>();
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(getDefaultGeneralServerErrorID())
            .severity(CommonConstants.INTERNAL_SERVER_ERROR_SEVERITY)
            .statusDesc(CommonConstants.INTERNAL_SERVER_ERROR_DESCRIPTION).build();
    additionalStatusList.add(additionalStatus);

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }

  /*
   * ======================= Customized Exceptions =============================================
   */
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected ErrorRestResponse handleValidationException(HttpServletResponse httpServletResponse,
      ValidationException ex) {
    log.error(ex.getMessage(), ex);

    List<AdditionalStatus> additionalStatusList = new ArrayList<AdditionalStatus>();
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(ex.getServerError().getServerStatusCode())
            .severity(ex.getServerError().getSeverity())
            .statusDesc(ex.getServerError().getStatusDesc()).build();
    additionalStatusList.add(additionalStatus);

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }

  @ExceptionHandler(SystemException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  protected ErrorRestResponse handleSystemException(HttpServletResponse httpServletResponse,
      SystemException ex) {
    log.error(ex.getMessage(), ex);

    List<AdditionalStatus> additionalStatusList = new ArrayList<AdditionalStatus>();
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(ex.getServerError().getServerStatusCode())
            .severity(ex.getServerError().getSeverity())
            .statusDesc(ex.getServerError().getStatusDesc()).build();
    additionalStatusList.add(additionalStatus);

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ResponseBody
  protected ErrorRestResponse handleResourceNotFoundException(
      HttpServletResponse httpServletResponse, ResourceNotFoundException ex) {
    log.error(ex.getMessage(), ex);

    List<AdditionalStatus> additionalStatusList = new ArrayList<AdditionalStatus>();
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(ex.getServerError().getServerStatusCode())
            .severity(ex.getServerError().getSeverity())
            .statusDesc(ex.getServerError().getStatusDesc()).build();
    additionalStatusList.add(additionalStatus);

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }

  @ExceptionHandler(ApplicationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected ErrorRestResponse handleApplicationException(HttpServletResponse httpServletResponse,
      ApplicationException ex) {
    log.error(ex.getMessage(), ex);

    List<AdditionalStatus> additionalStatusList = new ArrayList<AdditionalStatus>();
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(ex.getServerError().getServerStatusCode())
            .severity(ex.getServerError().getSeverity())
            .statusDesc(ex.getServerError().getStatusDesc()).build();
    additionalStatusList.add(additionalStatus);

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }


  @ExceptionHandler(InvalidRequestException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  protected ErrorRestResponse handleInvalidRequestException(HttpServletResponse httpServletResponse,
      InvalidRequestException ex) {
    log.error(ex.getMessage(), ex);

    List<AdditionalStatus> additionalStatusList = new ArrayList<AdditionalStatus>();
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(ex.getServerError().getServerStatusCode())
            .severity(ex.getServerError().getSeverity())
            .statusDesc(ex.getServerError().getStatusDesc()).build();
    additionalStatusList.add(additionalStatus);

    Status errorStatus =
        Status.builder().serverStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
            .severity(Status.Severity.ERROR).additionalStatus(additionalStatusList).build();

    return ErrorRestResponse.builder().status(errorStatus).build();
  }

  private AdditionalStatus toAdditionalStatus(FieldError fieldError) {
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(getDefaultValidationErrorID())
            .severity(CommonConstants.VALIDATION_ERROR_SEVERITY)
            .statusDesc(fieldError.getDefaultMessage()).build();

    return additionalStatus;
  }

  private AdditionalStatus toAdditionalStatus(ConstraintViolation violation) {
    AdditionalStatus additionalStatus =
        AdditionalStatus.builder().serverStatusCode(getDefaultValidationErrorID())
            .severity(CommonConstants.VALIDATION_ERROR_SEVERITY).statusDesc(violation.getMessage())
            .build();

    return additionalStatus;
  }
}
