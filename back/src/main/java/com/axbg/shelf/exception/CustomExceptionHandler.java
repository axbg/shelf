package com.axbg.shelf.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    private ResponseEntity<String> handleCustomException(CustomException ex) {
        System.out.println("CustomException caught");
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

}
