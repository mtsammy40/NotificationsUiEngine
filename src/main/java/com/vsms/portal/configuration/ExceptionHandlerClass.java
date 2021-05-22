package com.vsms.portal.configuration;

import com.vsms.portal.api.requests.PostMessageRequest;
import com.vsms.portal.api.responses.ApiResponse;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.utils.enums.ApiStatus;
import com.vsms.portal.utils.enums.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

        // Add any auth errors if present -> These are added to request object in the Jwt Filter
        List<String> errorsList = new ArrayList<>();
        Object errors = req.getAttribute(Strings.REQUEST_ATTRIBUTE_ERRORS_KEY.getValue());
        if(errors != null) {
            errorsList = (List<String>) errors;
        }

        if (e.getThrowable() != null) {
            LOG.error("Error in controller | cause: {} | {}", e.getThrowable().getClass(), e.getThrowable().getMessage());
        } else {
            LOG.error("Error in controller | {}", e.getMessage());
        }
        errorsList.add(e.getMessage());

        return new ApiResponse<>(e.getApiStatus(), null).withErrors(errorsList).build();
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
