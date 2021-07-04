package com.scalable.exchange.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.scalable.exchange.client.ECBStatsDataFetchClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateService {

  private static final String EXCHANGE_RATE_FOR_PAIR = "Exchange rate for pair %s/%s is";

  private final ECBStatsDataFetchClient ecbStatsDataFetchClient;

  public Map<String, Double> getReferenceRatePerCurrency(String currency) {

    Map<String, Double> stringDoubleMap = retrieveAllCurrencyRate().entrySet().stream()
        .filter(currencyRate -> currencyRate.getKey().equalsIgnoreCase(currency))
        .findFirst().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k));

    RequestedCurrency.getInstance().requestedCurrency.get(currency).addAndGet(1);

    return stringDoubleMap;
  }

  public Map<String, Double> getExchangeRateForPair(String currency1, String currency2) {

    Map<String, Double> referenceRateC1 = getReferenceRatePerCurrency(currency1);
    Map<String, Double> referenceRateC2 = getReferenceRatePerCurrency(currency2);

    Map<String, Double> exchangeRateForPair = new HashMap<>();
    exchangeRateForPair.put(String.format(EXCHANGE_RATE_FOR_PAIR, currency1, currency2),
        calculateExchangeRate(referenceRateC1.get(currency1), referenceRateC2.get(currency2)));

    return exchangeRateForPair;

  }

  public String getConvertedCurrencyAmount(String fromCurrency, double amount, String toCurrency) {

    Map<String, Double> exchangeRate = getExchangeRateForPair(fromCurrency, toCurrency);
    double convertedAmount = exchangeRate.get(String.format(EXCHANGE_RATE_FOR_PAIR, fromCurrency, toCurrency)) * amount;
    return String.format("%s %s = %s %s", amount, fromCurrency, convertedAmount, toCurrency);

  }

  public Map<String, AtomicInteger> retrieveSupportedCurrenciesWithRequestedNumber() {
    return RequestedCurrency.getInstance().requestedCurrency;
  }

  private double calculateExchangeRate(double value1, double value2) {
    return value2 / value1;
  }

  private Map<String, Double> retrieveAllCurrencyRate() {

    Document document = ecbStatsDataFetchClient.fetchEsbStatsData();
    Map<String, Double> currencyRateMap = new HashMap<>();
    currencyRateMap.put("EUR", 1.00);

    document.select("tbody").select("tr").forEach(element -> {
      currencyRateMap.put(element.childNode(1).childNode(0).childNode(0).toString(),
          Double.valueOf(element.childNode(5).childNode(0).childNode(1).childNode(0).toString()));
    });

    return currencyRateMap;
  }

}
