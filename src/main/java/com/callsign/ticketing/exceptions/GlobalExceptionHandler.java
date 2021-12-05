package com.callsign.ticketing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.AbstractMap;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity handleIllegalArgumentException(Exception exception){
    return getErrorFilledResponseEntity(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  private ResponseEntity getErrorFilledResponseEntity(String exceptionMessage, HttpStatus status){
    AbstractMap.SimpleEntry<String, String> response =
        new AbstractMap.SimpleEntry<>("Message", exceptionMessage);
    return ResponseEntity.status(status).body(response);
  }
}
