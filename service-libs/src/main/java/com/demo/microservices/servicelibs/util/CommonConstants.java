package com.demo.microservices.servicelibs.util;

import com.demo.microservices.servicelibs.dto.AdditionalStatus;

public class CommonConstants {
  
    public static final String REQUEST_PAYLOAD_URI = "uri";
    public static final String REQUEST_PAYLOAD_METHOD = "method";
    public static final String REQUEST_PAYLOAD_BODY = "requestBody";
    
    // Invalid Request Error
    public static final String INVALID_REQUEST_DESCRIPTION = "Invalid request parameters provided.";
    public static final AdditionalStatus.Severity INVALID_REQUEST_SEVERITY = AdditionalStatus.Severity.ERROR;

    // Validation Error
    public static final String VALIDATION_ERROR_DESCRIPTION = "Validation Error.";
    public static final AdditionalStatus.Severity VALIDATION_ERROR_SEVERITY = AdditionalStatus.Severity.ERROR;
    
    // Internal Server Error
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = "Your request could not be completed due to an internal error, please contact your administrator.";
    public static final AdditionalStatus.Severity INTERNAL_SERVER_ERROR_SEVERITY =  AdditionalStatus.Severity.ERROR;

    // System Exception
    public static final String SYSTEM_ERROR_DESCRIPTION = "Your request could not be completed due to an internal error, please contact your administrator.";
    public static final AdditionalStatus.Severity SYSTEM_ERROR_SEVERITY = AdditionalStatus.Severity.ERROR;
    
    // Application Exception
    public static final String APPLICATION_ERROR_DESCRIPTION = "Your request could not be completed due to an internal error, please contact your administrator.";
    public static final AdditionalStatus.Severity APPLICATION_ERROR_SEVERITY = AdditionalStatus.Severity.ERROR;
    
    public static final String RESOURCE_NOT_FOUND_ERROR_DESCRIPTION = "Resource you asking for does not exist, please contact your administrator.";
    public static final AdditionalStatus.Severity RESOURCE_NOT_FOUND_ERROR_SEVERITY = AdditionalStatus.Severity.ERROR;
    
    // Error codes
    public static final String RESOURCE_NOT_FOUND_ERROR_ID = "demo-common-err-0001";
    
    public static final String PROJECT_ID_VALIDATION_MESSAGE = "Project ID is invalid.";
}

