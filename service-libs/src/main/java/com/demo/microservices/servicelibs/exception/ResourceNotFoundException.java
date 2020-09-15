package com.demo.microservices.servicelibs.exception;

import com.demo.microservices.servicelibs.dto.AdditionalStatus;
import com.demo.microservices.servicelibs.util.CommonConstants;

/*
 * @author kaihe
 * 
 */

public class ResourceNotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String serverStatusCode) {
        super(serverStatusCode, CommonConstants.RESOURCE_NOT_FOUND_ERROR_DESCRIPTION, CommonConstants.RESOURCE_NOT_FOUND_ERROR_SEVERITY);
    }

    public ResourceNotFoundException(String serverStatusCode, String statusDesc) {
        super(serverStatusCode, statusDesc, CommonConstants.RESOURCE_NOT_FOUND_ERROR_SEVERITY);
    }
    
    public ResourceNotFoundException(String serverStatusCode, String statusDesc, AdditionalStatus.Severity severity) {
        super(serverStatusCode, statusDesc, severity);
    }
}
