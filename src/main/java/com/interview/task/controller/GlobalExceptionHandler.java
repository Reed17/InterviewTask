package com.interview.task.controller;

import com.interview.task.dto.ApiErrorResponse;
import com.interview.task.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidOrEmptyAmountException.class)
    public ApiErrorResponse handleInvalidOrEmptyResponseException(final InvalidOrEmptyAmountException ex) {
        return getApiErrorResponse(ex.getCause(), ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(LowBalanceException.class)
    public ApiErrorResponse handleLowBalanceException(final LowBalanceException ex) {
        return getApiErrorResponse(ex.getCause(), ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ApiErrorResponse handleUserAlreadyExistsException(final UserAlreadyExistsException ex) {
        return getApiErrorResponse(ex.getCause(), ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ApiErrorResponse handleUserNotFoundException(final UserNotFoundException ex) {
        return getApiErrorResponse(ex.getCause(), ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(WalletCreationLimitException.class)
    public ApiErrorResponse handleWalletCreationLimitException(final WalletCreationLimitException ex) {
        return getApiErrorResponse(ex.getCause(), ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(WalletWithPointedCurrencyAlreadyExistsException.class)
    public ApiErrorResponse handleWalletWithPointedCurrencyAlreadyExistsException(final WalletWithPointedCurrencyAlreadyExistsException ex) {
        return getApiErrorResponse(ex.getCause(), ex.getClass().getSimpleName(), ex.getMessage());
    }

    private ApiErrorResponse getApiErrorResponse(
            final Throwable throwable,
            final String simpleName,
            final String message) {
        final String cause = String.valueOf(throwable);
        LOG.error(cause);
        return new ApiErrorResponse(simpleName, cause, message);
    }
}
