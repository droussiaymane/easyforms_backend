package com.stackroute.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler({CatchException.class, ConstraintViolationException.class})
    public ResponseEntity<?> resourceNotFoundHandling(CatchException exception, WebRequest request){
        ErrorResponse errorResponse =
                new ErrorResponse(exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
        ErrorResponse errorResponse =
                new ErrorResponse(exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
