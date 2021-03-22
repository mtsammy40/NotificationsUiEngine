package com.vsms.portal.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vsms.portal.utils.enums.ApiStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private List<String> errors;

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
     * @param apiStatus
     * @param alternateResponseMessage
     * @param data
     */
    public ApiResponse(ApiStatus apiStatus, String alternateResponseMessage, T data) {
        this.apiStatus = apiStatus;
        this.data = data;
        this.code = apiStatus.getCode();
        this.message = alternateResponseMessage;
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

    public ResponseEntity<ApiResponse<T>> build() {
        return new ResponseEntity<ApiResponse<T>>(this, this.apiStatus.getDefaultHttpStatus());
    }
}
