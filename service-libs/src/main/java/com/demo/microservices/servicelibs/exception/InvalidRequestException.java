package com.demo.microservices.servicelibs.exception;

import com.demo.microservices.servicelibs.util.CommonConstants;

/*
 * @author hekai27
 * 
 */

public class InvalidRequestException extends BaseException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException(String serverStatusCode) {
        super(serverStatusCode, CommonConstants.INVALID_REQUEST_DESCRIPTION, CommonConstants.INVALID_REQUEST_SEVERITY);
    }

    public InvalidRequestException(String serverStatusCode, String statusDescription) {
        super(serverStatusCode, statusDescription, CommonConstants.INVALID_REQUEST_SEVERITY);
    }
}