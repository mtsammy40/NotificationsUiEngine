package com.vsms.portal.exception;

import org.springframework.http.ResponseEntity;

public class RestCallException extends Exception {
    ResponseEntity<String> responseEntity;
    public RestCallException(String message) {
        super(message);
    }

    public RestCallException(String message, ResponseEntity<String> responseEntity) {
        super(message);
        this.responseEntity = responseEntity;
    }
}
