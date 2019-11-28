package com.jha.project.rest.exception;

public class ValueNotFoundException extends RuntimeException {
    public ValueNotFoundException(final String message) {
        super(message);
    }
}
