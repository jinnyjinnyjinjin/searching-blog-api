package com.myproject.searchingblogs.app.handler;

import com.myproject.searchingblogs.app.controller.ApiResponse;
import com.myproject.searchingblogs.app.exception.BlogSearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BlogSearchException.class)
    private ResponseEntity<ApiResponse> handleBlogSearchException(BlogSearchException ex) {
        log.error("Error occurred. message: {}, ", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse(ex), HttpStatus.BAD_REQUEST);
    }
}
