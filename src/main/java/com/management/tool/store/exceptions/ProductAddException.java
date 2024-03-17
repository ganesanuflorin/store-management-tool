package com.management.tool.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ProductAddException extends RuntimeException{

    public ProductAddException(String message) {
        super(message);
    }

    public ProductAddException(String message, Throwable cause) {
        super(message, cause);
    }
}
