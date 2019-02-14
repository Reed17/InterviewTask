package com.interview.task.converter;

import com.interview.task.enums.Currency;

public interface Converter {
    Double convert(Double amount, Currency convertFrom, Currency convertTo);
}
