package com.interview.task.service;

import com.interview.task.converter.Converter;
import com.interview.task.dto.WalletDto;
import com.interview.task.entity.Wallet;
import com.interview.task.exceptions.InvalidOrEmptyAmountException;
import com.interview.task.exceptions.LowBalanceException;
import com.interview.task.mapper.WalletMapper;
import com.interview.task.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.interview.task.utils.WalletValidatorUtil.amountChecker;
import static com.interview.task.utils.WalletValidatorUtil.checkCurrentBalance;

/**
 * Class represents WalletService implementation.
 */
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

    /**
     * Method preforms replenish balance operation between two wallets.
     *
     * @param clientWalletFrom wallet id which balance will be reduced
     * @param clientWalletTo wallet id which balance will be replenished
     * @param amount money amount
     * @return true if operation successful, false otherwise
     */
    @Transactional
    @Override
    public boolean replenishBalance(final Long clientWalletFrom, final Long clientWalletTo, final Double amount) {
        subtract(clientWalletFrom, amount);
        add(clientWalletTo, amount);
        return true;
    }

    /**
     * Method performs replenish balance operation between two wallet with different currencies.
     *
     * @param clientWalletFrom wallet id which balance will be reduced
     * @param clientWalletTo wallet id which balance will be replenished
     * @param amount money amount
     * @return true if operation is successful, false otherwise
     */
    @Transactional
    @Override
    public boolean replenishBalanceByDifferentCurrencies(final Long clientWalletFrom, final Long clientWalletTo, final Double amount) {
        final Wallet from = walletRepository.getOne(clientWalletFrom);
        final Wallet to = walletRepository.getOne(clientWalletTo);
        if (!(from.getCurrency().getTypeValue().equals(to.getCurrency().getTypeValue()))) {
            checkCurrentBalance(amount, from.getBalance());
            Double convertedSum = converter.convert(amount, from.getCurrency(), to.getCurrency());
            reduceBalance(amount, from);
            walletRepository.save(from);
            addBalance(convertedSum, to);
            walletRepository.save(to);
            return true;
        }
        return false;
    }

    /**
     * Method performs balance increasing operation for certain wallet.
     *
     * @param clientWalletId wallet id which balance will increase
     * @param amount money amount
     * @throws InvalidOrEmptyAmountException can be thrown if money amount value is incorrect or empty
     */
    @Transactional
    @Override
    public void add(final Long clientWalletId, final Double amount) throws InvalidOrEmptyAmountException {
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        amountChecker(amount);
        addBalance(amount, clientWallet);
        walletRepository.save(clientWallet);
    }

    /**
     * Method performs increase balance processing.
     *
     * @param amount money amount
     * @param clientWallet wallet  which balance will increase
     */
    private void addBalance(final Double amount, final Wallet clientWallet) {
        Double currentBalance = clientWallet.getBalance();
        currentBalance += amount;
        clientWallet.setBalance(currentBalance);
    }

    /**
     * Method performs balance decreasing operation for certain wallet.
     *
     * @param clientWalletId wallet id which balance will will decrease
     * @param amount money amount
     * @throws InvalidOrEmptyAmountException can be thrown if money amount value is incorrect or empty
     * @throws LowBalanceException can be thrown if balance is low
     */
    @Transactional
    @Override
    public void subtract(final Long clientWalletId, final Double amount) throws InvalidOrEmptyAmountException, LowBalanceException {
        final Wallet clientWallet = walletRepository.getOne(clientWalletId);
        amountChecker(amount);
        reduceBalance(amount, clientWallet);
        walletRepository.save(clientWallet);
    }

    /**
     * Method performs balance decrease processing.
     *
     * @param amount money amount
     * @param clientWallet client wallet which balance will decrease
     */
    private void reduceBalance(final Double amount, final Wallet clientWallet) {
        Double currentBalance = clientWallet.getBalance();
        checkCurrentBalance(amount, currentBalance);
        currentBalance -= amount;
        clientWallet.setBalance(currentBalance);
    }

    /**
     * Method returns wallet by id.
     *
     * @param walletId wallet id
     * @return WalletDto
     */
    @Override
    public WalletDto getWallet(final Long walletId) {
        final Wallet wallet = walletRepository.getOne(walletId);
        return walletMapper.toDto(wallet);
    }

    /**
     * Method removes wallet by id.
     *
     * @param walletId wallet id
     */
    @Override
    public void removeWallet(final Long walletId) {
        walletRepository.deleteById(walletId);
    }

}
