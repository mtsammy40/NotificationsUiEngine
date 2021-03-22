package com.vsms.portal.exception;

import com.vsms.portal.utils.enums.ApiStatus;

public class ApiOperationException extends Exception {
    private ApiStatus apiStatus;

    public ApiOperationException(String message, ApiStatus status) {
        super(message);
        this.apiStatus = status;
    }

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }
}
