package com.interview.task.service;

import com.interview.task.dto.ClientDto;
import com.interview.task.dto.WalletDto;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto);
    Optional<ClientDto> getClient(Long clientId);
    ClientDto updateClient(ClientDto clientDto);
    void removeClient(Long clientId);
    List<ClientDto> getAllClients();
    List<WalletDto> getAllClientWallets(Long clientId);
}
