package com.interview.task.service;

import com.interview.task.dto.ClientDto;
import com.interview.task.entity.Wallet;
import com.interview.task.exceptions.InvalidOrEmptyAmountException;
import com.interview.task.exceptions.LowBalanceException;

public interface WalletService {
     boolean replenishBalance(Long clientWalletFrom, Long clientWalletTo, Double amount);
     void add(Long walletId, Double amount) throws InvalidOrEmptyAmountException;
     void subtract(Long walletId, Double amount) throws InvalidOrEmptyAmountException, LowBalanceException;
}