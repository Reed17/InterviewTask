package com.interview.task.converter;

import com.interview.task.config.CurrencyRatesConfig;
import com.interview.task.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConverterImpl implements Converter {

    private CurrencyRatesConfig currencyRatesConfig;

    @Autowired
    public ConverterImpl(CurrencyRatesConfig currencyRatesConfig) {
        this.currencyRatesConfig = currencyRatesConfig;
    }

    @Override
    public Double convert(Double amount, Currency convertFrom, Currency convertTo) {
        Double result = 0.0;
        final Map<Currency, List<Double>> currentRates = getCourseRate(convertFrom);
        if (convertFrom.getTypeValue().equals("UAH")) {
            if (convertTo.getTypeValue().equals("USD")) {
                result = amount * currentRates.get(convertFrom).get(0);
            } else {
                result = amount * currentRates.get(convertFrom).get(1);
            }
        } else if (convertFrom.getTypeValue().equals("USD")) {
            if (convertTo.getTypeValue().equals("UAH")) {
                result = amount * currentRates.get(convertFrom).get(0);
            } else {
                result = amount * currentRates.get(convertFrom).get(1);
            }
        } else if (convertFrom.getTypeValue().equals("EUR")) {
            if (convertTo.getTypeValue().equals("UAH")) {
                result = amount * currentRates.get(convertFrom).get(0);
            } else {
                result = amount * currentRates.get(convertFrom).get(1);
            }
        } else {
            // todo handle some unpredictable situation
        }
        return result;
    }

    // todo return current course rate for one currency
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
        return new HashMap<Currency, List<Double>>() {{ put(currency, fromCurrency); }};
    }
}
