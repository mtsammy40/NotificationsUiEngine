package com.vsms.portal.configuration;

import com.vsms.portal.api.requests.PostMessageRequest;
import com.vsms.portal.api.responses.ApiResponse;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.utils.enums.ApiStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerClass {
    private static final Logger LOG = LogManager.getLogger(ExceptionHandler.class);

    @ExceptionHandler(value = ApiOperationException.class)
    public ResponseEntity<?>
    apiOperationExceptionHandler(HttpServletRequest req, ApiOperationException e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        LOG.error("Error in controller | {}", e.getMessage());

        return new ApiResponse<>(e.getApiStatus(), null).build();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?>
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        e.printStackTrace();

        return new ApiResponse<>(ApiStatus.UNKNOWN_ERROR, null).build();
    }
}
