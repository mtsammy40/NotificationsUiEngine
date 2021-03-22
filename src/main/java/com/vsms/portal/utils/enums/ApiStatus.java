package com.vsms.portal.utils.enums;

import org.springframework.http.HttpStatus;

public enum ApiStatus {
    // Success -> 000 -> 099
    OK("000", "Ok", HttpStatus.OK),

    // Client initiated errors -> 900 - 999
    BAD_REQUEST("901", "Bad Request", HttpStatus.BAD_REQUEST),

    // Server side errors -> 800 - 899
    UNKNOWN_ERROR("801", "Unknown Error", HttpStatus.INTERNAL_SERVER_ERROR),

    // Third party errors -> 700 - 799
    THIRD_PARTY_UNREACHABLE("701", "Third party unreachable", HttpStatus.FAILED_DEPENDENCY),

    // Authentication errors -> 600 - 699
    INVALID_CREDENTIALS("601", "Invalid Credentials", HttpStatus.UNAUTHORIZED),
    INSUFFICIENT_PERMISSIONS("602", "You are not allowed to access this resource", HttpStatus.FORBIDDEN);

    private String code;
    private String message;
    private HttpStatus defaultHttpStatus;

    ApiStatus(String code, String defaultMessage, HttpStatus defaultHttpStatus) {
        this.code = code;
        this.message = defaultMessage;
        this.defaultHttpStatus = defaultHttpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getDefaultHttpStatus() {
        return defaultHttpStatus;
    }
}
