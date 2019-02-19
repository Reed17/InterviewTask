package com.interview.task.entity;

import com.interview.task.enums.Currency;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_gen")
    @SequenceGenerator(name = "wallet_gen", sequenceName = "wallet_seq")
    private Long walletId;

    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    private Double balance;

    public Wallet() {
    }

    public Wallet(Currency currency, Double balance) {
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
                '}';
    }
}
