package com.axbg.shelf.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
class CustomException extends Exception {
    private final String message;
    private final HttpStatus status;
}
