package com.interview.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "currency")
public class CurrencyRatesConfig {
    @NestedConfigurationProperty
    private Uah uah;
    @NestedConfigurationProperty
    private Usd usd;
    @NestedConfigurationProperty
    private Eur eur;

    public class Uah {
        private Double usd;
        private Double eur;

        public Double getUsd() {
            return usd;
        }

        public void setUsd(Double usd) {
            this.usd = usd;
        }

        public Double getEur() {
            return eur;
        }

        public void setEur(Double eur) {
            this.eur = eur;
        }
    }

    public class Usd {
        private Double uah;
        private Double eur;

        public Double getUah() {
            return uah;
        }

        public void setUah(Double uah) {
            this.uah = uah;
        }

        public Double getEur() {
            return eur;
        }

        public void setEur(Double eur) {
            this.eur = eur;
        }
    }

    public class Eur {
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

    public Uah getUah() {
        return uah;
    }

    public void setUah(Uah uah) {
        this.uah = uah;
    }

    public Usd getUsd() {
        return usd;
    }

    public void setUsd(Usd usd) {
        this.usd = usd;
    }

    public Eur getEur() {
        return eur;
    }

    public void setEur(Eur eur) {
        this.eur = eur;
    }
}
