package com.sgarlea.todo.exception;

public class NoResourceException extends RuntimeException {

    public NoResourceException() {
    }

    public NoResourceException(String message) {
        super(message);
    }

}
