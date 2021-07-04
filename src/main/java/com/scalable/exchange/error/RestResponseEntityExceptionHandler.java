package com.scalable.exchange.error;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.scalable.exchange.config.ErrorConstants;
import com.scalable.exchange.dto.Error;
import com.scalable.exchange.dto.ErrorAttributes;
import com.scalable.exchange.error.exception.DataFetchingException;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private final ErrorConstants errorConstants;

  @ExceptionHandler(DataFetchingException.class)
  public ResponseEntity<Error> handleGameOverException(DataFetchingException ex) {

    Error error =
        getErrorResponseEntity(errorConstants.getDataFetchingException(), HttpStatus.INTERNAL_SERVER_ERROR, ex);

    log.error("{} :: {}", errorConstants.getDataFetchingException().getCode(),
        errorConstants.getDataFetchingException().getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Error getErrorResponseEntity(ErrorAttributes errorAttributes, HttpStatus httpStatus, Exception ex) {

    Error error = new Error();
    error.setErrors(
        List.of(ErrorAttributes.builder().code(errorAttributes.getCode()).message(errorAttributes.getMessage())
            .status(httpStatus.value()).params(List.of(ex.getMessage())).build()));

    return error;
  }
}
