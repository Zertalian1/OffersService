package com.example.controller;

import com.example.exception.ChangeOfferStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ChangeOfferStatusException.class)
    public ResponseEntity<Void> databaseError() {
        return ResponseEntity.badRequest().build();
    }
}
