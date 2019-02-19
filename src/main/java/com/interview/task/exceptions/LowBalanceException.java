package com.interview.task.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LowBalanceException extends RuntimeException {
    public LowBalanceException(final String message) {
        super(message);
    }
}
