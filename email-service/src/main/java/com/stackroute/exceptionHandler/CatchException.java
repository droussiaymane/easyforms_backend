package com.stackroute.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CatchException extends Exception {

    public CatchException(String message) {
        super(message);
    }
}