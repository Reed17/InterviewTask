package com.interview.task.utils;

import com.interview.task.dto.WalletDto;
import com.interview.task.enums.Currency;
import com.interview.task.exceptions.WalletWithPointedCurrencyAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WalletCurrencyMatcherUtil {

    public static void checkAvailableCurrencyForCreation(Currency currencyToAdd, List<WalletDto> clientWallets) {
        for (WalletDto clientWallet : clientWallets) {
            if (clientWallet.getCurrency().getTypeValue().equals(currencyToAdd.getTypeValue())) {
                final String message = String.format("You've already created wallet with currency %s", currencyToAdd.getTypeValue());
                throw new WalletWithPointedCurrencyAlreadyExistsException(message);
            }
        };
    }
}
