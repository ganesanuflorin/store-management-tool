package com.management.tool.store.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductAddException.class, ProductNotFoundException.class})
    public final ResponseEntity<Object> handleConflict(RuntimeException e, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (e instanceof ProductAddException) {
            HttpStatus status = HttpStatus.CONFLICT;
            ProductAddException pae = (ProductAddException) e;
            return handleProductAddException(pae, headers, status, request);
        }else if(e instanceof ProductNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ProductNotFoundException pnfe = (ProductNotFoundException) e;
            return handleProductNotFoundException(pnfe, headers, status, request);
        }else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(e, null, headers, status, request);
        }
    }

    private ResponseEntity<Object> handleProductAddException(ProductAddException pae, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(pae.getMessage(), headers, status);
    }

    private ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException pnfe, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(pnfe.getMessage(), headers, status);
    }

    private ResponseEntity<Object> handleExceptionInternal(RuntimeException e, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("InternalServerError", headers, status);
    }
}
