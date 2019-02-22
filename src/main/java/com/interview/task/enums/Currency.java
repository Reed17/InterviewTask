package com.interview.task.enums;

/**
 * Represents currencies.
 */
public enum Currency {
    UAH(1, "UAH"),
    USD(2, "USD"),
    EUR(3, "EUR");

    private final Integer typeKey;
    private final String typeValue;

    Currency(Integer typeKey, String typeValue) {
        this.typeKey = typeKey;
        this.typeValue = typeValue;
    }

    public Integer getTypeKey() {
        return typeKey;
    }

    public String getTypeValue() {
        return typeValue;
    }
}
