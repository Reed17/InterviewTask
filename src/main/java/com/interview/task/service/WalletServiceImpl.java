package com.interview.task.service;

import com.interview.task.converter.Converter;
import com.interview.task.entity.Wallet;
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
        amountChecker(amount);
        try {
            subtract(clientWalletFrom, amount);
            add(clientWalletTo, amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean replenishBalanceByDifferentCurrencies(final Long clientWalletFrom, final Long clientWalletTo, final Double amount) {
        Wallet from = walletRepository.getOne(clientWalletFrom);
        Wallet to = walletRepository.getOne(clientWalletTo);
        if (!(from.getCurrency().getTypeValue().equals(to.getCurrency().getTypeValue()))) {
            try {
                // todo check current balance
                checkCurrentBalance(amount, from.getBalance());
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
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void add(final Long clientWalletId, final Double amount) throws InvalidOrEmptyAmountException {
        amountChecker(amount);
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
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
        amountChecker(amount);
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        reduceBalance(amount, clientWallet);
        walletRepository.save(clientWallet);
    }

    private void reduceBalance(Double amount, Wallet clientWallet) {
        Double currentBalance = clientWallet.getBalance();
        checkCurrentBalance(amount, currentBalance);
        currentBalance -= amount;
        clientWallet.setBalance(currentBalance);
    }

    private void checkCurrentBalance(final Double amount, final Double currentBalance) {
        if (currentBalance < amount) {
            throw new LowBalanceException(String.format("Your balance is low : %.2f", currentBalance));
        }
    }

    private void amountChecker(final Double amount) throws InvalidOrEmptyAmountException {
        if (amount == null || amount < 1) {
            throw new InvalidOrEmptyAmountException(String.format("Invalid or empty amount : %.2f", amount));
        }
    }
}
