package com.interview.task.exceptions;

public class WalletWithPointedCurrencyAlreadyExistsException extends RuntimeException {
    public WalletWithPointedCurrencyAlreadyExistsException(String message) {
        super(message);
    }
}
