package com.demo.microservices.servicelibs.exception;

import com.demo.microservices.servicelibs.dto.AdditionalStatus;
import com.demo.microservices.servicelibs.util.CommonConstants;

/*
 * @author hekai27
 * 
 * Business Error, such as business logic validation failed, not able to perform any further action.
 * 
 */
public class ApplicationException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ApplicationException(String serverStatusCode) {
        super(serverStatusCode, CommonConstants.APPLICATION_ERROR_DESCRIPTION, CommonConstants.APPLICATION_ERROR_SEVERITY);
    }

    public ApplicationException(String serverStatusCode, String statusDesc) {
        super(serverStatusCode, statusDesc, CommonConstants.APPLICATION_ERROR_SEVERITY);
    }
    
    public ApplicationException(String serverStatusCode, String statusDesc, AdditionalStatus.Severity severity) {
        super(serverStatusCode, statusDesc, severity);
    }
}

