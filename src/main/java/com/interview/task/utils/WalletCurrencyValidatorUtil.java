package com.interview.task.utils;

import com.interview.task.dto.WalletDto;
import com.interview.task.enums.Currency;
import com.interview.task.enums.Message;
import com.interview.task.exceptions.WalletWithPointedCurrencyAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WalletCurrencyValidatorUtil {

    public static void checkAvailableCurrencyForCreation(
            final Currency currencyToAdd,
            final List<WalletDto> userWallets) {
        for (final WalletDto userWallet : userWallets) {
            if (userWallet.getCurrency().getTypeValue().equals(currencyToAdd.getTypeValue())) {
                throw new WalletWithPointedCurrencyAlreadyExistsException(
                        Message.WALLET_WITH_CURRENCY_ALREADY_EXISTS.getMsgBody());
            }
        };
    }
}
