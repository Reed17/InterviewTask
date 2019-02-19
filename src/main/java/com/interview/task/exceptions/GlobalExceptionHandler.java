package com.interview.task.exceptions;

import com.interview.task.dto.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
                    InvalidOrEmptyAmountException.class,
                    LowBalanceException.class,
                    UserAlreadyExistsException.class,
                    UserNotFoundException.class,
                    WalletCreationLimitException.class,
                    WalletWithPointedCurrencyAlreadyExistsException.class})
    public ApiErrorResponse handleInvalidOrEmptyResponseException(InvalidOrEmptyAmountException ex) {
        final String cause = String.valueOf(ex.getCause());
        LOG.error(cause);
        return new ApiErrorResponse(ex.getClass().getSimpleName(), cause, ex.getMessage());
    }

}
