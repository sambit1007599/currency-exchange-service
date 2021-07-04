package com.scalable.exchange.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.scalable.exchange.service.CurrencyRateService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
public class CurrencyExchangeController {

  private final CurrencyRateService currencyRateService;

  @GetMapping("/reference-rate/{currency}")
  public ResponseEntity<Map<String, Double>> getReferenceRatePerCurrency(
      @PathVariable("currency") @NotBlank String currency) {
    log.info("getReferenceRatePerCurrency method of CurrencyExchangeController request input is : {}", currency);
    return new ResponseEntity<>(currencyRateService.getReferenceRatePerCurrency(currency), HttpStatus.OK);
  }

  @GetMapping("/exchange-rate/{currency1}/{currency2}")
  public ResponseEntity<Map<String, Double>> getExchangeRateForPair(
      @PathVariable("currency1") @NotBlank String currency1,
      @PathVariable("currency2") @NotBlank String currency2) {
    log.info("getExchangeRateForPair method of CurrencyExchangeController request input is : {}/{}", currency1,
        currency2);
    return new ResponseEntity<>(currencyRateService.getExchangeRateForPair(currency1, currency2), HttpStatus.OK);
  }

  @GetMapping("/currency-converter/{fromCurrency}/{toCurrency}/{amount}")
  public ResponseEntity<String> getConvertedCurrencyAmount(
      @PathVariable("fromCurrency") @NotBlank String fromCurrency,
      @PathVariable("toCurrency") @NotBlank String toCurrency,
      @PathVariable("amount") @NotNull double amount) {
    log.info("getExchangeRateForPair method of CurrencyExchangeController request input is : {} {} to {}", amount,
        fromCurrency, toCurrency);
    return new ResponseEntity<>(currencyRateService.getConvertedCurrencyAmount(fromCurrency, amount, toCurrency),
        HttpStatus.OK);
  }

  @GetMapping("/supported-currency")
  public ResponseEntity<Map<String, AtomicInteger>> getSupportedCurrencies() {
    return new ResponseEntity<>(currencyRateService.retrieveSupportedCurrenciesWithRequestedNumber(), HttpStatus.OK);
  }

}
