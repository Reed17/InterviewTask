package com.interview.task.controller;

import com.interview.task.dto.ClientDto;
import com.interview.task.dto.WalletDto;
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
    public ClientController(ClientService clientService, WalletService walletService) {
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

    public ResponseEntity<?> addBalance(@PathVariable("id") final Long clientId) {
        List<WalletDto> clientWallets = clientService.getAllClientWallets(clientId);
        WalletDto walletDto = clientWallets.get(0);
        //walletService.add();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
