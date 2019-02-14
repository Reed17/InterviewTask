package com.interview.task.enums;

public enum Currency {
    UAH(1, "uah"),
    USD(2, "usd"),
    EUR(3, "eur");

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
