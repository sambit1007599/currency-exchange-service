package com.scalable.exchange.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;

@UtilityClass
@Slf4j
public class DataFeeder {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static Document getDocument() {

    try {
      return Jsoup.parse(new File(DataFeeder.class.getClassLoader().getResource("document.html").getFile()),
          StandardCharsets.UTF_8.name());
    } catch (IOException e) {
      log.error("IOException occured while fetching document.html data : ", e);
      throw new RuntimeException(e.getMessage());
    }
  }

  public static Map<String, Double> getCurrencyMap() {
    try {
      return mapper.readValue(new File(
          DataFeeder.class.getClassLoader().getResource("currencyReferenceData.json").getFile()), Map.class);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e.getMessage());
    }
  }

}
