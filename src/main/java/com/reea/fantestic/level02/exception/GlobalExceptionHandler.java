package com.reea.fantestic.level02.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SkyNotFoundException.class})
    public ResponseEntity<Object> handleSkyNotFound(SkyNotFoundException e) {
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }
}
