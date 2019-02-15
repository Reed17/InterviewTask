package com.interview.task.exceptions;

public class WalletCreationLimitException extends RuntimeException {
    public WalletCreationLimitException(String message) {
        super(message);
    }
}
