package com.sgarlea.todo.controller.resources;

import org.springframework.http.HttpStatus;

public class DefaultErrorResource {

    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return status;
    }

    public DefaultErrorResource setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DefaultErrorResource setMessage(String message) {
        this.message = message;
        return this;
    }
}
