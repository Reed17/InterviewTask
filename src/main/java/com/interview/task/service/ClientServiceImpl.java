package com.interview.task.service;

import com.interview.task.dto.ClientDto;
import com.interview.task.dto.WalletDto;
import com.interview.task.entity.Client;
import com.interview.task.entity.Wallet;
import com.interview.task.exceptions.WalletCreationLimitException;
import com.interview.task.mapper.ClientMapper;
import com.interview.task.mapper.WalletMapper;
import com.interview.task.repository.ClientRepository;
import com.interview.task.repository.WalletRepository;
import com.interview.task.utils.WalletCurrencyMatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final WalletRepository walletRepository;
    private final ClientMapper clientMapper;
    private final WalletMapper walletMapper;

    @Autowired
    public ClientServiceImpl(final ClientRepository clientRepository,
                             final WalletRepository walletRepository,
                             final ClientMapper clientMapper,
                             final WalletMapper walletMapper) {
        this.clientRepository = clientRepository;
        this.walletRepository = walletRepository;
        this.clientMapper = clientMapper;
        this.walletMapper = walletMapper;
    }

    @Transactional
    @Override
    public ClientDto createClient(final ClientDto clientDto) {
        final Client newClient = parseToClientEntity(clientDto);
        return parseToClientDto(clientRepository.save(newClient));
    }

    @Override
    public Optional<ClientDto> getClient(final Long clientId) {
        final ClientDto clientDto = parseToClientDto(clientRepository.getOne(clientId));
        return Optional.ofNullable(clientDto);
    }

    @Override
    public ClientDto updateClient(final ClientDto clientDto) {
        final Client client = clientRepository.getOne(clientDto.getClientId());
        client.setClientId(clientDto.getClientId());
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword());
        return parseToClientDto(clientRepository.save(client));
    }

    @Override
    public void removeClient(final Long clientId) {
        final Client client = clientRepository.getOne(clientId);
        clientRepository.delete(client);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream().map(this::parseToClientDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public WalletDto addWallet(final Long clientId, final WalletDto walletDto) throws WalletCreationLimitException {
        List<WalletDto> clientWallets = clientRepository.getAllClientWallets(clientId);
        // check whether wallet with pointed currency already exists
        WalletCurrencyMatcherUtil.checkAvailableCurrencyForCreation(walletDto.getCurrency(), clientWallets);
        Client client = clientRepository.getOne(clientId);
        if (clientWallets.size() < 3) {
            // add wallet
            Wallet savedWallet = walletRepository.save(parseToWalletEntity(walletDto));
            client.getWallets().add(savedWallet);
            clientRepository.save(client);
            return parseToWalletDto(savedWallet);
        } else {
            // deny wallet adding
            throw new WalletCreationLimitException("You can create only 3 different wallets!");
        }
    }

    @Override
    public void removeWallet(final Long walletId) {
        final Wallet wallet = walletRepository.getOne(walletId);
        walletRepository.delete(wallet);
    }

    @Transactional
    @Override
    public List<WalletDto> getAllClientWallets(final Long clientId) {
        return clientRepository.getAllClientWallets(clientId);
    }

    private Client parseToClientEntity(ClientDto clientDto) {
        return clientMapper.toEntity(clientDto);
    }

    private ClientDto parseToClientDto(Client client) {
        return clientMapper.toDto(client);
    }

    private Wallet parseToWalletEntity(WalletDto walletDto) {
        return walletMapper.toEntity(walletDto);
    }

    private WalletDto parseToWalletDto(Wallet wallet) {
        return walletMapper.toDto(wallet);
    }
}
