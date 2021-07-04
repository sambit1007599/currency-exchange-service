# Exchange Rate Service

## Description

The service shall expose an API, which can be consumed by other services. Overview of service

- I want to retrieve the ECB reference rate for a currency pair, e.g. USD/EUR or HUF/EUR.
- I want to retrieve an exchange rate for other pairs, e.g. HUF/USD.
- I want to retrieve a list of supported currencies and see how many times they were requested.
- I want to convert an amount in a given currency to another, e.g. 15 EUR = ??? GBP

## Reference

The European Central Bank publishes reference exchange rates on a daily basis. You can find them
here: `https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_excha nge_rates/html/index.en.html`

The ECB uses EUR as the base currency. You can calculate non-EUR rates from the published data set.