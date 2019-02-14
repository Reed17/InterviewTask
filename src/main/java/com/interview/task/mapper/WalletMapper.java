package com.interview.task.mapper;

import com.interview.task.dto.WalletDto;
import com.interview.task.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public Wallet toEntity(WalletDto walletDto) {
        final Wallet wallet = new Wallet();
        wallet.setWalletId(walletDto.getWalletId());
        wallet.setCurrency(walletDto.getCurrency());
        wallet.setBalance(walletDto.getBalance());
        //wallet.setClient(walletDto.getClient());
        return wallet;
    }

    public WalletDto toDto(Wallet wallet) {
        final WalletDto walletDto = new WalletDto();
        walletDto.setWalletId(wallet.getWalletId());
        walletDto.setCurrency(wallet.getCurrency());
        walletDto.setBalance(wallet.getBalance());
        //walletDto.setClient(wallet.getClient());
        return walletDto;
    }
}