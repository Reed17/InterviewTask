package com.interview.task.utils;

import com.interview.task.dto.WalletDto;
import com.interview.task.enums.Currency;
import com.interview.task.enums.Message;
import com.interview.task.exceptions.InvalidOrEmptyAmountException;
import com.interview.task.exceptions.LowBalanceException;
import com.interview.task.exceptions.WalletNotFoundException;
import com.interview.task.exceptions.WalletWithPointedCurrencyAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class represents wallet validating utility.
 */
@Component
public class WalletValidatorUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WalletValidatorUtil.class);

    /**
     * Method checks whether the user has an ability to create new wallet.
     *
     * @param currencyToAdd currency for new wallet
     * @param userWallets existing user wallets
     */
    public static void checkAvailableCurrencyForCreation(
            final Currency currencyToAdd,
            final List<WalletDto> userWallets) {
        for (final WalletDto userWallet : userWallets) {
            if (userWallet.getCurrency().getTypeValue().equals(currencyToAdd.getTypeValue())) {
                final String msgBody = Message.WALLET_WITH_CURRENCY_ALREADY_EXISTS.getMsgBody();
                LOG.error(msgBody);
                throw new WalletWithPointedCurrencyAlreadyExistsException(
                        msgBody);
            }
        };
    }

    /**
     * Method checks current wallet balance whether it has correct value to perform certain operation.
     *
     * @param amount money amount
     * @param currentBalance current balance
     */
    public static void checkCurrentBalance(final Double amount, final Double currentBalance) {
        if (currentBalance < amount) {
            final String msgBody = Message.LOW_BALANCE.getMsgBody();
            LOG.error(msgBody);
            throw new LowBalanceException(msgBody);
        }
    }

    /**
     * Method performs amount validation.
     *
     * @param amount money amount
     * @throws InvalidOrEmptyAmountException can be thrown if money amount value is incorrect or empty
     */
    public static void amountChecker(final Double amount) throws InvalidOrEmptyAmountException {
        if (amount == null || amount < 1) {
            final String msgBody = Message.EMPTY_AMOUNT.getMsgBody();
            LOG.error(msgBody);
            throw new InvalidOrEmptyAmountException(msgBody);
        }
    }

    /**
     * Method checks wallet presence in database.
     *
     * @param isWalletPresent true if wallet is present false otherwise
     */
    public static void checkWalletPresence(boolean isWalletPresent) {
        if (!isWalletPresent) {
            final String msgBody = Message.WALLET_NOT_FOUND.getMsgBody();
            LOG.error(msgBody);
            throw new WalletNotFoundException(msgBody);
        }
    }
}
