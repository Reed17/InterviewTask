package com.interview.task.controller;

import com.interview.task.dto.ClientDto;
import com.interview.task.dto.WalletDto;
import com.interview.task.exceptions.WalletCreationLimitException;
import com.interview.task.service.ClientService;
import com.interview.task.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ClientController {

    private final ClientService clientService;
    private final WalletService walletService;

    @Autowired
    public ClientController(final ClientService clientService, final WalletService walletService) {
        this.clientService = clientService;
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody final ClientDto clientDto) {
        return ResponseEntity.ok().body(clientService.createClient(clientDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable("id") final Long clientId) {
        return ResponseEntity.ok().body(clientService.getClient(clientId));
    }

    @GetMapping
    public ResponseEntity<?> getAllClients() {
        return ResponseEntity.ok().body(clientService.getAllClients());
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody final ClientDto clientDto) {
        return ResponseEntity.ok().body(clientService.updateClient(clientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") final Long clientId) {
        clientService.removeClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/wallets")
    public ResponseEntity<?> getClientsWallets(@PathVariable("id") final Long clientId) {
        return ResponseEntity.ok().body(clientService.getAllClientWallets(clientId));
    }

    @PutMapping("/{id}/amount/add")
    public ResponseEntity<?> addBalance(@PathVariable("id") final Long clientId,
                                        @RequestParam("amount") final Double amount) {
        final List<WalletDto> clientWallets = clientService.getAllClientWallets(clientId);
        WalletDto walletDto = clientWallets.get(0);
        walletService.add(walletDto.getWalletId(), amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/amount/reduce")
    public ResponseEntity<?> reduceBalance(@PathVariable("id") final Long clientId,
                                           @RequestParam("amount") final Double amount) {
        final List<WalletDto> clientWallets = clientService.getAllClientWallets(clientId);
        WalletDto walletDto = clientWallets.get(0);
        walletService.subtract(walletDto.getWalletId(), amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{fromId}/replenish/{toId}")
    public ResponseEntity<?> replenishClientBalance(@PathVariable("fromId") final Long fromId,
                                                    @RequestParam("amount") final Double amount,
                                                    @PathVariable("toId") final Long toId) {
        final List<WalletDto> fromWallets = clientService.getAllClientWallets(fromId);
        WalletDto fromWallet = fromWallets.get(0);
        final List<WalletDto> toWallets = clientService.getAllClientWallets(toId);
        WalletDto toWallet = toWallets.get(0);
        boolean isReplenished = walletService.replenishBalance(fromWallet.getWalletId(), toWallet.getWalletId(), amount);
        return ResponseEntity.ok().body(isReplenished);
    }

    @PutMapping("/{fromId}/multicurr/{toId}")
    public ResponseEntity<?> replenishBalanceMultiCurrency(@PathVariable("fromId") final Long fromId,
                                                           @RequestParam("amount") final Double amount,
                                                           @PathVariable("toId") final Long toId) {
        final List<WalletDto> fromWallets = clientService.getAllClientWallets(fromId);
        WalletDto fromWallet = fromWallets.get(0);
        final List<WalletDto> toWallets = clientService.getAllClientWallets(toId);
        WalletDto toWallet = toWallets.get(0);
        boolean isMultiCurrReplenished = walletService.replenishBalanceByDifferentCurrencies(fromId, toId, amount);
        return ResponseEntity.ok().body(isMultiCurrReplenished);
    }

    @PostMapping("/{id}/wallet/new")
    public ResponseEntity<?> createNewClientWallet(@PathVariable("id") final Long clientId,
                                                   @RequestBody final WalletDto walletDto) throws WalletCreationLimitException {
        WalletDto createdWallet = clientService.addWallet(clientId, walletDto);
        return ResponseEntity.ok().body(createdWallet);
    }
}
