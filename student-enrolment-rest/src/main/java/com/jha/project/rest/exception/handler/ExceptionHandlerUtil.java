package com.jha.project.rest.exception.handler;

import com.jha.project.rest.exception.model.ApiException;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerUtil {

    public static final ResponseEntity fromApiException(final ApiException apiException) {
        return new ResponseEntity(apiException, apiException.getStatus());
    }
}
