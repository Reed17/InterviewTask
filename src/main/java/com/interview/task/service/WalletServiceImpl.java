package com.interview.task.service;

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

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
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
    public void add(final Long clientWalletId, final Double amount) throws InvalidOrEmptyAmountException {
        amountChecker(amount);
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        clientWallet.setBalance(clientWallet.getBalance() + amount);
        walletRepository.save(clientWallet);
    }

    @Transactional
    @Override
    public void subtract(Long clientWalletId, Double amount) throws InvalidOrEmptyAmountException, LowBalanceException {
        amountChecker(amount);
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        Double currentBalance = clientWallet.getBalance();
        if (currentBalance < amount) {
            throw new LowBalanceException(String.format("Your balance is low : %.2f", currentBalance));
        }
        currentBalance -= amount;
        clientWallet.setBalance(currentBalance);
        walletRepository.save(clientWallet);
    }

    private void amountChecker(final Double amount) throws InvalidOrEmptyAmountException {
        if (amount == null || amount < 1) {
            throw new InvalidOrEmptyAmountException(String.format("Invalid or empty amount : %.2f", amount));
        }
    }
}
