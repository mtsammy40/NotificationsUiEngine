package com.vsms.portal.exception;

import com.vsms.portal.utils.enums.ApiStatus;

public class ApiOperationException extends Exception {
    private ApiStatus apiStatus;
    private Throwable throwable;

    public ApiOperationException(String message, ApiStatus status) {
        super(message);
        this.apiStatus = status;
    }

    public ApiOperationException(String message, ApiStatus status, Throwable throwable) {
        super(message);
        this.apiStatus = status;
        this.throwable = throwable;
    }

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
