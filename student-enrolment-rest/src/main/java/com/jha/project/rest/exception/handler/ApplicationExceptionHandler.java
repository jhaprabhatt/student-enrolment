package com.jha.project.rest.exception.handler;


import com.jha.project.rest.exception.ValueNotFoundException;
import com.jha.project.rest.exception.model.ApiException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex) {
        return ExceptionHandlerUtil.fromApiException(
                ApiException
                        .builder()
                        .reason(ex.getMessage())
                        .status(BAD_REQUEST)
                        .code(BAD_REQUEST.toString())
                        .build()
        );
    }

    @ExceptionHandler(ValueNotFoundException.class)
    public ResponseEntity handleValueNotFoundException(ValueNotFoundException ex) {
        return ExceptionHandlerUtil.fromApiException(
                ApiException
                        .builder()
                        .reason(ex.getMessage())
                        .status(NOT_FOUND)
                        .code(NOT_FOUND.toString())
                        .build()
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity handleDataAccessException(DataAccessException ex) {
        return ExceptionHandlerUtil.fromApiException(
                ApiException
                        .builder()
                        .reason(ex.getMessage())
                        .status(NOT_FOUND)
                        .code(NOT_FOUND.toString())
                        .build()
        );
    }
}
