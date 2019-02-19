package com.interview.task.converter;

import com.interview.task.config.EurCurrencyRateProperties;
import com.interview.task.config.UahCurrencyRateProperties;
import com.interview.task.config.UsdCurrencyRateProperties;
import com.interview.task.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterImpl implements Converter {

    private final UahCurrencyRateProperties uahCurrencyRateProperties;
    private final UsdCurrencyRateProperties usdCurrencyRateProperties;
    private final EurCurrencyRateProperties eurCurrencyRateProperties;

    @Autowired
    public ConverterImpl(
            final UahCurrencyRateProperties uahCurrencyRateProperties,
            final UsdCurrencyRateProperties usdCurrencyRateProperties,
            final EurCurrencyRateProperties eurCurrencyRateProperties) {
        this.uahCurrencyRateProperties = uahCurrencyRateProperties;
        this.usdCurrencyRateProperties = usdCurrencyRateProperties;
        this.eurCurrencyRateProperties = eurCurrencyRateProperties;
    }

    @Override
    public Double convert(final Double amount, final Currency convertFrom, final Currency convertTo) {
        double result = 0.0;
        switch (convertFrom.getTypeValue()) {
            case "UAH":
                if (convertTo.getTypeValue().equals("USD")) {
                    result = amount * uahCurrencyRateProperties.getUsd();
                } else {
                    result = amount * uahCurrencyRateProperties.getEur();
                }
                break;
            case "USD":
                if (convertTo.getTypeValue().equals("UAH")) {
                    result = amount * usdCurrencyRateProperties.getUah();
                } else {
                    result = amount * usdCurrencyRateProperties.getEur();
                }
                break;
            case "EUR":
                if (convertTo.getTypeValue().equals("UAH")) {
                    result = amount * eurCurrencyRateProperties.getUah();
                } else {
                    result = amount * eurCurrencyRateProperties.getUsd();
                }
                break;
        }
        return result;
    }

}
