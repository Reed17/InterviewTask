package com.interview.task.converter;

import com.interview.task.config.CurrencyRatesConfig;
import com.interview.task.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConverterImpl implements Converter {

    private final CurrencyRatesConfig currencyRatesConfig;

    @Autowired
    public ConverterImpl(final CurrencyRatesConfig currencyRatesConfig) {
        this.currencyRatesConfig = currencyRatesConfig;
    }

    @Override
    public Double convert(Double amount, Currency convertFrom, Currency convertTo) {
        Double result = 0.0;
        final Map<Currency, List<Double>> allRates = getCourseRate(convertFrom);
        final List<Double> singleRateValues = allRates.get(convertFrom);
        // todo think is it real to handle with streams?
        if (convertFrom.getTypeValue().equals("UAH")) {
            if (convertTo.getTypeValue().equals("USD")) {
                // todo  think can we handle here with streams?
                // todo how to remove if/else ???
                result = amount * singleRateValues.get(0);
            } else {
                result = amount * singleRateValues.get(1);
            }
        } else if (convertFrom.getTypeValue().equals("USD")) {
            if (convertTo.getTypeValue().equals("UAH")) {
                result = amount * singleRateValues.get(0);
            } else {
                result = amount * singleRateValues.get(1);
            }
        } else if (convertFrom.getTypeValue().equals("EUR")) {
            if (convertTo.getTypeValue().equals("UAH")) {
                result = amount * singleRateValues.get(0);
            } else {
                result = amount * singleRateValues.get(1);
            }
        }
        return result;
    }

    private Map<Currency, List<Double>> getCourseRate(Currency currency) {
        Double uahCourseRate;
        Double usdCourseRate;
        Double eurCourseRate;
        List<Double> fromCurrency = new ArrayList<>();
        switch (currency.getTypeValue()) {
            case "UAH":
                CurrencyRatesConfig.Uah uahRate = currencyRatesConfig.getUah();
                usdCourseRate = uahRate.getUsd();
                eurCourseRate = uahRate.getEur();
                fromCurrency.addAll(Arrays.asList(usdCourseRate, eurCourseRate));
                break;
            case "USD":
                CurrencyRatesConfig.Usd usdRate = currencyRatesConfig.getUsd();
                uahCourseRate = usdRate.getUah();
                eurCourseRate = usdRate.getEur();
                fromCurrency.addAll(Arrays.asList(uahCourseRate, eurCourseRate));
                break;
            case "EUR":
                CurrencyRatesConfig.Eur eurRate = currencyRatesConfig.getEur();
                uahCourseRate = eurRate.getUah();
                usdCourseRate = eurRate.getUsd();
                fromCurrency.addAll(Arrays.asList(uahCourseRate, usdCourseRate));
                break;
        }
        return new HashMap<>() {{ put(currency, fromCurrency); }};
    }
}
