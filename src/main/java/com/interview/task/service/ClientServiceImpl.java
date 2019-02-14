package com.interview.task.service;

import com.interview.task.dto.ClientDto;
import com.interview.task.dto.WalletDto;
import com.interview.task.entity.Client;
import com.interview.task.entity.Wallet;
import com.interview.task.mapper.ClientMapper;
import com.interview.task.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientServiceImpl(final ClientRepository clientRepository, final ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional
    @Override
    public ClientDto createClient(final ClientDto clientDto) {
        final Client newClient = parseToEntity(clientDto);
        final ClientDto createdClientDto = parseToDto(clientRepository.save(newClient));
        return createdClientDto;
    }

    @Override
    public Optional<ClientDto> getClient(final Long clientId) {
        final ClientDto clientDto = parseToDto(clientRepository.getOne(clientId));
        return Optional.ofNullable(clientDto);
    }

    @Override
    public ClientDto updateClient(final ClientDto clientDto) {
        final Client client = clientRepository.getOne(clientDto.getClientId());
        client.setClientId(clientDto.getClientId());
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword());
        final ClientDto updatedDto = parseToDto(clientRepository.save(client));
        return updatedDto;
    }

    @Override
    public void removeClient(final Long clientId) {
        final Client client = clientRepository.getOne(clientId);
        clientRepository.delete(client);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream().map(this::parseToDto).collect(Collectors.toList());
    }

    public List<WalletDto> getAllClientWallets(final Long clientId) {
        return clientRepository.getAllClientWallets(clientId);
    }

    private Client parseToEntity(ClientDto clientDto) {
        return clientMapper.toEntity(clientDto);
    }

    private ClientDto parseToDto(Client client) {
        return clientMapper.toDto(client);
    }
}
