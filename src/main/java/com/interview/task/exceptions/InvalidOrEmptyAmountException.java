package com.interview.task.exceptions;

public class InvalidOrEmptyAmountException extends RuntimeException {
    public InvalidOrEmptyAmountException(String message) {
        super(message);
    }
}
