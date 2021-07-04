package com.scalable.exchange.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scalable.exchange.service.CurrencyRateService;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeControllerTest {

  @Mock
  private CurrencyRateService currencyRateService;

  private CurrencyExchangeController currencyExchangeController;

  @BeforeEach
  public void setUp() {
    currencyExchangeController = new CurrencyExchangeController(currencyRateService);
  }

  @Test
  @DisplayName("Test getReferenceRatePerCurrency of CurrencyExchangeController")
  public void testGetReferenceRatePerCurrency() {

    String currency = "GBP";
    double amount = 1;
    Map<String, Double> currencyMap = new HashMap<>();
    currencyMap.put(currency, amount);

    when(currencyRateService.getReferenceRatePerCurrency(currency)).thenReturn(currencyMap);

    ResponseEntity<Map<String, Double>> actualCurrencyMap =
        currencyExchangeController.getReferenceRatePerCurrency(currency);

    assertThat(actualCurrencyMap.getBody()).isEqualTo(currencyMap);
    assertThat(actualCurrencyMap.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

  @Test
  @DisplayName("Test getExchangeRateForPair of CurrencyExchangeController")
  public void testGetExchangeRateForPair() {

    String currency1 = "HUF";
    String currency2 = "USD";
    String key = "Exchange rate for pair HUF/USD is";
    double value = 0.003357852882703777;
    Map<String, Double> currencyMap = new HashMap<>();
    currencyMap.put(key, value);

    when(currencyRateService.getExchangeRateForPair(currency1, currency2)).thenReturn(currencyMap);

    ResponseEntity<Map<String, Double>> actualCurrencyMap =
        currencyExchangeController.getExchangeRateForPair(currency1, currency2);

    assertThat(actualCurrencyMap.getBody()).isEqualTo(currencyMap);
    assertThat(actualCurrencyMap.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

  @Test
  @DisplayName("Test getConvertedCurrencyAmount of CurrencyExchangeController")
  public void testGetConvertedCurrencyAmount() {

    String currency1 = "EUR";
    String currency2 = "GBP";
    double amount = 15;
    String expected = "15.0 EUR = 12.90045 GBP";

    when(currencyRateService.getConvertedCurrencyAmount(currency1, amount, currency2)).thenReturn(expected);

    ResponseEntity<String> resultValue =
        currencyExchangeController.getConvertedCurrencyAmount(currency1, currency2, amount);

    assertThat(resultValue.getBody()).isEqualTo(expected);
    assertThat(resultValue.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

}
