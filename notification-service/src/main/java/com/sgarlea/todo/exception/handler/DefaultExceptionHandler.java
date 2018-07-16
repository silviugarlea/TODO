package com.sgarlea.todo.exception.handler;

import com.sgarlea.todo.controller.resources.DefaultErrorResource;
import com.sgarlea.todo.exception.NoResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoResourceException.class})
    public ResponseEntity<DefaultErrorResource> handleResourceNotFound(RuntimeException ex) {
        DefaultErrorResource resource = new DefaultErrorResource()
                .setStatus(HttpStatus.NOT_FOUND)
                .setMessage(ex.getMessage());
        return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
    }

}
