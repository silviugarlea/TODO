package com.sgarlea.todo.exception;

public class ServiceUnavaibleException extends RuntimeException {

    public ServiceUnavaibleException() {
    }

    public ServiceUnavaibleException(String message) {
        super(message);
    }

}
