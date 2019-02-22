package com.interview.task.entity;

import com.interview.task.enums.Currency;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class represents wallet entity.
 */
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    private Double balance;

    private boolean isMulticurrent;

    public Wallet() {
    }

    public Wallet(Currency currency, Double balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public Wallet(Currency currency, Double balance, boolean isMulticurrent) {
        this.currency = currency;
        this.balance = balance;
        this.isMulticurrent = isMulticurrent;
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

    public boolean isMulticurrent() {
        return isMulticurrent;
    }

    public void setMulticurrent(boolean multicurrent) {
        isMulticurrent = multicurrent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(walletId, wallet.walletId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", currency=" + currency +
                ", balance=" + balance +
                ", isMulticurrent=" + isMulticurrent +
                '}';
    }
}
