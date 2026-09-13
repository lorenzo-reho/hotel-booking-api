package com.booking.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResourceAccessException extends RuntimeException {
    public ResourceAccessException(String message) {
        super(message);
    }
}