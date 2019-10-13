package com.moneymakers.tradingapp;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.gdax.GDAXStreamingExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingAppApplication.class, args);
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(GDAXStreamingExchange.class.getName());

// Connect to the Exchange WebSocket API. Blocking wait for the connection.
        exchange.connect(ProductSubscription.create().addTicker(CurrencyPair.BTC_USD).build()).blockingAwait();

// Subscribe to live trades update.
        exchange.getStreamingMarketDataService()
                .getTicker(CurrencyPair.BTC_USD)
                .subscribe(trade -> {
                    System.out.println("trade.getAsk() = " + trade.getAsk());
                });

    }


}
