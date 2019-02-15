package com.interview.task.service;

import com.interview.task.converter.Converter;
import com.interview.task.entity.Wallet;
import com.interview.task.enums.Currency;
import com.interview.task.exceptions.InvalidOrEmptyAmountException;
import com.interview.task.exceptions.LowBalanceException;
import com.interview.task.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;
    private Converter converter;

    @Autowired
    public WalletServiceImpl(final WalletRepository walletRepository, final Converter converter) {
        this.walletRepository = walletRepository;
        this.converter = converter;
    }

    @Transactional
    @Override
    public boolean replenishBalance(final Long clientWalletFrom, final Long clientWalletTo, final Double amount) {
        subtract(clientWalletFrom, amount);
        add(clientWalletTo, amount);
        return true;
    }

    @Transactional
    @Override
    public boolean replenishBalanceByDifferentCurrencies(final Long clientWalletFrom, final Long clientWalletTo, final Double amount) {
        Wallet from = walletRepository.getOne(clientWalletFrom);
        Wallet to = walletRepository.getOne(clientWalletTo);
        if (!(from.getCurrency().getTypeValue().equals(to.getCurrency().getTypeValue()))) {
            // todo check current balance
            checkCurrentBalance(amount, from.getBalance(), from.getCurrency());
            Double convertedSum = converter.convert(amount, from.getCurrency(), to.getCurrency());
            System.out.println(convertedSum);
            // todo do reduce operation first
            reduceBalance(amount, from);
            // todo save operation
            walletRepository.save(from);
            // todo add balance
            addBalance(convertedSum, to);
            // todo save operation
            walletRepository.save(to);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void add(final Long clientWalletId, final Double amount) throws InvalidOrEmptyAmountException {
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        amountChecker(amount, clientWallet.getCurrency());
        addBalance(amount, clientWallet);
        walletRepository.save(clientWallet);
    }

    private void addBalance(Double amount, Wallet clientWallet) {
        Double currentBalance = clientWallet.getBalance();
        currentBalance += amount;
        clientWallet.setBalance(currentBalance);
    }

    @Transactional
    @Override
    public void subtract(Long clientWalletId, Double amount) throws InvalidOrEmptyAmountException, LowBalanceException {
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        amountChecker(amount, clientWallet.getCurrency());
        reduceBalance(amount, clientWallet);
        walletRepository.save(clientWallet);
    }

    private void reduceBalance(Double amount, Wallet clientWallet) {
        Double currentBalance = clientWallet.getBalance();
        checkCurrentBalance(amount, currentBalance, clientWallet.getCurrency());
        currentBalance -= amount;
        clientWallet.setBalance(currentBalance);
    }

    private void checkCurrentBalance(final Double amount, final Double currentBalance, final Currency currency) {
        if (currentBalance < amount) {
            throw new LowBalanceException(String.format("Your balance is low : %.2f %s", currentBalance, currency.getTypeValue()));
        }
    }

    private void amountChecker(final Double amount, final Currency currency) throws InvalidOrEmptyAmountException {
        if (amount == null || amount < 1) {
            throw new InvalidOrEmptyAmountException(String.format("Invalid or empty amount : %.2f %s", amount, currency.getTypeValue()));
        }
    }
}
