package com.experis.caritocomprarest.web.exceptionhandlers;

import com.experis.caritocomprarest.exceptions.NotAddedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotAddedExceptionHandler {

    @ExceptionHandler(NotAddedException.class)
    public ResponseEntity<String> hadleNotFound(NotAddedException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_MODIFIED);
    }
}
