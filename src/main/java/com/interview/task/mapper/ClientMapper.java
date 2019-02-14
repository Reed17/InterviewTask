package com.interview.task.mapper;

import com.interview.task.dto.ClientDto;
import com.interview.task.entity.Client;
import com.interview.task.entity.Wallet;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public Client toEntity(ClientDto clientDto) {
        final Client client = new Client();
        client.setClientId(clientDto.getClientId());
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword());
        if (clientDto.getWallets() == null) {
            client.setWallets(new HashSet<Wallet>());
        } else {
            client.setWallets(clientDto.getWallets());
        }
        return client;
    }

    public ClientDto toDto(Client client) {
        final ClientDto clientDto = new ClientDto();
        clientDto.setClientId(client.getClientId());
        clientDto.setName(client.getName());
        clientDto.setEmail(client.getEmail());
        clientDto.setPassword(client.getPassword());
        if (client.getWallets() == null) {
            clientDto.setWallets(new HashSet<Wallet>());
        } else {
            clientDto.setWallets(client.getWallets());
        }
        return clientDto;
    }
}
