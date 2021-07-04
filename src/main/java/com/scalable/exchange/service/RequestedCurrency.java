package com.scalable.exchange.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;

import com.scalable.exchange.client.ECBStatsDataFetchClient;

public class RequestedCurrency {

  private static RequestedCurrency single_instance = null;

  public Map<String, AtomicInteger> requestedCurrency;

  private RequestedCurrency() {
    requestedCurrency = initialize();
  }

  private Map<String, AtomicInteger> initialize() {

    ECBStatsDataFetchClient ecbStatsDataFetchClient = new ECBStatsDataFetchClient();
    Document document = ecbStatsDataFetchClient.fetchEsbStatsData();
    Map<String, AtomicInteger> currencyRateMap = new HashMap<>();
    currencyRateMap.put("EUR", new AtomicInteger());

    document.select("tbody").select("tr").forEach(element -> {
      currencyRateMap.put(element.childNode(1).childNode(0).childNode(0).toString(), new AtomicInteger());
    });

    return currencyRateMap;
  }

  public static RequestedCurrency getInstance() {
    if (single_instance == null)
      single_instance = new RequestedCurrency();

    return single_instance;
  }
}
