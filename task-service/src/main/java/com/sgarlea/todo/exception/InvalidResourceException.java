package com.sgarlea.todo.exception;

public class InvalidResourceException extends RuntimeException {

    public InvalidResourceException() {
    }

    public InvalidResourceException(String message) {
        super(message);
    }

}
