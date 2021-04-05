package com.vsms.portal.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vsms.portal.utils.enums.ApiStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private List<String> errors = new ArrayList<>();

    @JsonIgnore
    private ApiStatus apiStatus;


    /**
     * Defaults to success
     * @param data
     */
    public ApiResponse(T data) {
        this.data = data;
        this.apiStatus = ApiStatus.OK;
        this.code = apiStatus.getCode();
        this.message = apiStatus.getMessage();
    }

    /**
     *
     * @param data
     */
    public ApiResponse(ApiStatus apiStatus, T data) {
        this.apiStatus = apiStatus;
        this.data = data;
        this.code = apiStatus.getCode();
        this.message = apiStatus.getMessage();
    }

    /**
     *
     * @param {ApiStatus} apiStatus
     * @param {String} alternateResponseMessage
     * @param {T} data
     */
    public ApiResponse(ApiStatus apiStatus, String alternateResponseMessage, T data) {
        this.apiStatus = apiStatus;
        this.data = data;
        this.code = apiStatus.getCode();
        this.message = alternateResponseMessage;
    }

    /**
     *
     * @param {ApiStatus} apiStatus
     * @param {String} alternateResponseMessage
     * @param {String} error
     */
    public ApiResponse(ApiStatus apiStatus, String alternateResponseMessage, String error) {
        this.apiStatus = apiStatus;
        this.code = apiStatus.getCode();
        this.message = alternateResponseMessage;
        this.errors.add(error);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiResponse<?> addError(String error) {
        errors.add(error);
        return this;
    }

    public static <T> ApiResponse<T> ofError(ApiStatus apiStatus, String error) {
       return new ApiResponse<T>(apiStatus, error, error);
    }

    public ResponseEntity<ApiResponse<T>> build() {
        return new ResponseEntity<ApiResponse<T>>(this, this.apiStatus.getDefaultHttpStatus());
    }
}
