package com.scalable.exchange.error.exception;

import lombok.Getter;

@Getter
public class DataFetchingException extends RuntimeException {

  public DataFetchingException(final String message) {
    super(message);
  }
}
