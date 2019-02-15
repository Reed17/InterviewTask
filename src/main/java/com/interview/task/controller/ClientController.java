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

    @PutMapping("/add/{walletId}")
    public ResponseEntity<?> addBalance(@PathVariable("walletId") final Long walletId,
                                        @RequestParam("amount") final Double amount) {
        WalletDto walletDto = walletService.getWallet(walletId);
                walletService.add(walletDto.getWalletId(), amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reduce/{walletId}")
    public ResponseEntity<?> reduceBalance(@PathVariable("walletId") final Long walletId,
                                           @RequestParam("amount") final Double amount) {
        WalletDto walletDto = walletService.getWallet(walletId);
        walletService.subtract(walletDto.getWalletId(), amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{fromId}/replenish/{toId}")
    public ResponseEntity<?> replenishClientBalance(@PathVariable("fromId") final Long fromWalletId,
                                                    @RequestParam("amount") final Double amount,
                                                    @PathVariable("toId") final Long toWalletId) {
        WalletDto fromWallet = walletService.getWallet(fromWalletId);
        WalletDto toWallet = walletService.getWallet(toWalletId);
        boolean isReplenished = walletService.replenishBalance(fromWallet.getWalletId(), toWallet.getWalletId(), amount);
        return ResponseEntity.ok().body(isReplenished);
    }

    @PutMapping("/{fromId}/multicurr/{toId}")
    public ResponseEntity<?> replenishBalanceMultiCurrency(@PathVariable("fromId") final Long fromWalletId,
                                                           @RequestParam("amount") final Double amount,
                                                           @PathVariable("toId") final Long toWalletId) {
        boolean isMultiCurrReplenished = walletService.replenishBalanceByDifferentCurrencies(fromWalletId, toWalletId, amount);
        return ResponseEntity.ok().body(isMultiCurrReplenished);
    }

    @PostMapping("/{id}/wallet/new")
    public ResponseEntity<?> createNewClientWallet(@PathVariable("id") final Long clientId,
                                                   @RequestBody final WalletDto walletDto) throws WalletCreationLimitException {
        WalletDto createdWallet = clientService.addWallet(clientId, walletDto);
        return ResponseEntity.ok().body(createdWallet);
    }
}
