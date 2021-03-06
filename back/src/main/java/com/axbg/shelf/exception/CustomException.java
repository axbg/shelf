package com.axbg.shelf.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomException extends Exception {
    private final String message;
    private final HttpStatus status;

    @Override
    public String toString() {
        return "{ \"message\": \"" + message + "\" }";
    }
}
