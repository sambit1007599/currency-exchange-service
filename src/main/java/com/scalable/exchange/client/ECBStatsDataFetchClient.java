package com.scalable.exchange.client;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.scalable.exchange.error.exception.DataFetchingException;

@RequiredArgsConstructor
@Component
@Slf4j
public class ECBStatsDataFetchClient {

  private static final String ECB_URL =
      "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html";

  public Document fetchEsbStatsData() {
    try {
      return Jsoup.connect(ECB_URL).get();
    } catch (IOException e) {
      log.error("IOException occured while fetching data from ecb : ", e);
      throw new DataFetchingException(e.getMessage());
    }
  }
}
