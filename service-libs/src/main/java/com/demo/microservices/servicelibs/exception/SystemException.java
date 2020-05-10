package com.demo.microservices.servicelibs.exception;

import com.demo.microservices.servicelibs.util.CommonConstants;

/*
 * @author hekai27
 * 
 */

public class SystemException extends BaseException {

    private static final long serialVersionUID = 1L;

    public SystemException(String serverStatusCode) {
        super(serverStatusCode, CommonConstants.SYSTEM_ERROR_DESCRIPTION, CommonConstants.SYSTEM_ERROR_SEVERITY);
    }

    public SystemException(String serverStatusCode, String statusDesc) {
        super(serverStatusCode, statusDesc, CommonConstants.SYSTEM_ERROR_SEVERITY);
    }
}
