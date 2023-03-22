package com.myproject.searchingblogs.app.handler;

import com.myproject.searchingblogs.app.controller.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    protected ResponseEntity<ApiResponse> handleInternalServerErrorException(HttpServerErrorException.InternalServerError ex) {
        log.error("Error occurred. message: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Error occurred. message: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse(ex), HttpStatus.BAD_REQUEST);
    }
}
