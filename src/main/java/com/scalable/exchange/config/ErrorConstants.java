package com.scalable.exchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import com.scalable.exchange.dto.ErrorAttributes;

/**
 * The type Error constants.
 */
@Component
@ConfigurationProperties("constants.error")
@Getter
@Setter
public class ErrorConstants {

  private ErrorAttributes dataFetchingException;
}
