package com.interview.task.dto;

import com.interview.task.enums.Currency;

public class WalletDto {
    private Long walletId;
    private Currency currency;
    private Double balance;

    public WalletDto() {
    }

    public WalletDto(Currency currency, Double balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public WalletDto(Long walletId, Currency currency, Double balance) {
        this.walletId = walletId;
        this.currency = currency;
        this.balance = balance;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
