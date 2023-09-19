package com.example.solumonbackend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MemberException.class)
  public ErrorResponse handleAccountException(MemberException e) {
    log.error("{} is occurred", e.getErrorCode());
    return new ErrorResponse("Failed", e.getErrorCode(), e.getErrorMessage());
  }
}
