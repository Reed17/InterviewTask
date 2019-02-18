package com.interview.task.converter;

import com.interview.task.config.EurCurrencyRates;
import com.interview.task.config.UahCurrencyRates;
import com.interview.task.config.UsdCurrencyRates;
import com.interview.task.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterImpl implements Converter {

    private final UahCurrencyRates uahCurrencyRates;
    private final UsdCurrencyRates usdCurrencyRates;
    private final EurCurrencyRates eurCurrencyRates;

    @Autowired
    public ConverterImpl(
            final UahCurrencyRates uahCurrencyRates,
            final UsdCurrencyRates usdCurrencyRates,
            final EurCurrencyRates eurCurrencyRates) {
        this.uahCurrencyRates = uahCurrencyRates;
        this.usdCurrencyRates = usdCurrencyRates;
        this.eurCurrencyRates = eurCurrencyRates;
    }

    @Override
    public Double convert(Double amount, Currency convertFrom, Currency convertTo) {
        Double result = 0.0;
        if (convertFrom.getTypeValue().equals("UAH")) {
            if (convertTo.getTypeValue().equals("USD")) {
                result = amount * uahCurrencyRates.getUsd();
            } else {
                result = amount * uahCurrencyRates.getEur();
            }
        } else if (convertFrom.getTypeValue().equals("USD")) {
            if (convertTo.getTypeValue().equals("UAH")) {
                result = amount * usdCurrencyRates.getUah();
            } else {
                result = amount * usdCurrencyRates.getEur();
            }
        } else if (convertFrom.getTypeValue().equals("EUR")) {
            if (convertTo.getTypeValue().equals("UAH")) {
                result = amount * eurCurrencyRates.getUah();
            } else {
                result = amount * eurCurrencyRates.getUsd();
            }
        }
        return result;
    }

}
