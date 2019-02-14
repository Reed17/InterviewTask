package com.interview.task.dto;

import com.interview.task.entity.Wallet;
import lombok.*;

import java.util.Set;

public class ClientDto {
    private Long clientId;
    private String name;
    private String email;
    private String password;
    private Set<Wallet> wallets;

    public ClientDto() {
    }

    public ClientDto(String name, String email, String password, Set<Wallet> wallets) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.wallets = wallets;
    }

    public ClientDto(Long clientId, String name, String email, String password, Set<Wallet> wallets) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.wallets = wallets;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(Set<Wallet> wallets) {
        this.wallets = wallets;
    }
}
