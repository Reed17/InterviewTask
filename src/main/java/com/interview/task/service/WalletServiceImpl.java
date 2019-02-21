package com.interview.task.service;

import com.interview.task.converter.Converter;
import com.interview.task.dto.WalletDto;
import com.interview.task.entity.Wallet;
import com.interview.task.enums.Currency;
import com.interview.task.enums.Message;
import com.interview.task.exceptions.InvalidOrEmptyAmountException;
import com.interview.task.exceptions.LowBalanceException;
import com.interview.task.mapper.WalletMapper;
import com.interview.task.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final Converter converter;
    private final WalletMapper walletMapper;

    @Autowired
    public WalletServiceImpl(
            final WalletRepository walletRepository,
            final Converter converter,
            final WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.converter = converter;
        this.walletMapper = walletMapper;
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
        final Wallet from = walletRepository.getOne(clientWalletFrom);
        final Wallet to = walletRepository.getOne(clientWalletTo);
        if (!(from.getCurrency().getTypeValue().equals(to.getCurrency().getTypeValue()))) {
            checkCurrentBalance(amount, from.getBalance(), from.getCurrency());
            Double convertedSum = converter.convert(amount, from.getCurrency(), to.getCurrency());
            reduceBalance(amount, from);
            walletRepository.save(from);
            addBalance(convertedSum, to);
            walletRepository.save(to);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void add(final Long clientWalletId, final Double amount) throws InvalidOrEmptyAmountException {
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        amountChecker(amount);
        addBalance(amount, clientWallet);
        walletRepository.save(clientWallet);
    }

    private void addBalance(final Double amount, final Wallet clientWallet) {
        Double currentBalance = clientWallet.getBalance();
        currentBalance += amount;
        clientWallet.setBalance(currentBalance);
    }

    @Transactional
    @Override
    public void subtract(final Long clientWalletId, final Double amount) throws InvalidOrEmptyAmountException, LowBalanceException {
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        amountChecker(amount);
        reduceBalance(amount, clientWallet);
        walletRepository.save(clientWallet);
    }

    private void reduceBalance(final Double amount, final Wallet clientWallet) {
        Double currentBalance = clientWallet.getBalance();
        checkCurrentBalance(amount, currentBalance, clientWallet.getCurrency());
        currentBalance -= amount;
        clientWallet.setBalance(currentBalance);
    }

    @Override
    public WalletDto getWallet(final Long walletId) {
        final Wallet wallet = walletRepository.getOne(walletId);
        return walletMapper.toDto(wallet);
    }

    @Override
    public void removeWallet(final Long walletId) {
        walletRepository.deleteById(walletId);
    }

    private void checkCurrentBalance(final Double amount, final Double currentBalance, final Currency currency) {
        if (currentBalance < amount) {
            throw new LowBalanceException(Message.LOW_BALANCE.getMsgBody());
        }
    }

    private void amountChecker(final Double amount) throws InvalidOrEmptyAmountException {
        if (amount == null || amount < 1) {
            throw new InvalidOrEmptyAmountException(Message.EMPTY_AMOUNT.getMsgBody());
        }
    }
}
