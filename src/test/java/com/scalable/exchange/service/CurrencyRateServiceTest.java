package com.scalable.exchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.scalable.exchange.client.ECBStatsDataFetchClient;
import com.scalable.exchange.util.DataFeeder;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateServiceTest {

  @Mock
  private ECBStatsDataFetchClient ecbStatsDataFetchClient;

  private CurrencyRateService currencyRateService;

  private Document document;

  @BeforeEach
  public void setUp() {

    currencyRateService = new CurrencyRateService(ecbStatsDataFetchClient);

    document = DataFeeder.getDocument();
    when(ecbStatsDataFetchClient.fetchEsbStatsData()).thenReturn(document);
  }

  @Test
  @DisplayName("Test getReferenceRatePerCurrency of CurrencyRateService")
  public void testGetReferenceRatePerCurrency() {

    String currency = "GBP";
    Map<String, Double> currencyMap = DataFeeder.getCurrencyMap();
    Map<String, Double> actual = currencyRateService.getReferenceRatePerCurrency(currency);

    assertThat(actual.size()).isEqualTo(1);
    assertThat(actual.get(currency)).isEqualTo(currencyMap.get(currency));
    verify(ecbStatsDataFetchClient, times(1)).fetchEsbStatsData();
  }

  @Test
  @DisplayName("Test getExchangeRateForPair of CurrencyRateService")
  public void testGetExchangeRateForPair() {

    String currency1 = "HUF";
    String currency2 = "USD";
    String key = "Exchange rate for pair HUF/USD is";
    double value = 0.003357852882703777;

    Map<String, Double> actual = currencyRateService.getExchangeRateForPair(currency1, currency2);

    assertThat(actual.size()).isEqualTo(1);
    assertThat(actual.get(key)).isEqualTo(value);
    verify(ecbStatsDataFetchClient, times(2)).fetchEsbStatsData();
  }

  @Test
  @DisplayName("Test getConvertedCurrencyAmount of CurrencyRateService")
  public void testGetConvertedCurrencyAmount() {

    String currency1 = "EUR";
    String currency2 = "GBP";
    double amount = 15;
    String expected = "15.0 EUR = 12.90045 GBP";

    String actual = currencyRateService.getConvertedCurrencyAmount(currency1, amount, currency2);

    assertThat(actual).isEqualTo(expected);
    verify(ecbStatsDataFetchClient, times(2)).fetchEsbStatsData();
  }

}
