package com.interview.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "currency.eur")
public class EurCurrencyRates {
    private Double uah;
    private Double usd;

    public Double getUah() {
        return uah;
    }

    public void setUah(Double uah) {
        this.uah = uah;
    }

    public Double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }
}
