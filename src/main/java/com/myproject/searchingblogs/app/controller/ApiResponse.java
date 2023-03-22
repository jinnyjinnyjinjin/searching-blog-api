package com.myproject.searchingblogs.app.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse {
    private Object data;
    private String message;

    public ApiResponse(Object data) {
        this.data = data;
    }

    public ApiResponse(Exception ex) {
        this.message = ex.getMessage();
    }
}
