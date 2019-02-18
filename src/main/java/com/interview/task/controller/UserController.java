package com.interview.task.controller;

import com.interview.task.dto.UserDto;
import com.interview.task.dto.WalletDto;
import com.interview.task.exceptions.WalletCreationLimitException;
import com.interview.task.security.UserPrincipal;
import com.interview.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyAuthority('USER')")
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService clientService) {
        this.userService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return ResponseEntity.ok().body(userService.getUserById(userPrincipal.getUserId()));
    }

    @GetMapping
    public ResponseEntity<?> getAllClients() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody final UserDto userDto) {
        return ResponseEntity.ok().body(userService.updateUser(userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") final Long userId) {
        userService.removeUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/wallets")
    public ResponseEntity<?> getClientWallets(@PathVariable("id") final Long userId) {
        return ResponseEntity.ok().body(userService.getAllUserWallets(userId));
    }

    @PostMapping("/{id}/wallet/new")
    public ResponseEntity<?> createNewClientWallet(@PathVariable("id") final Long userId,
                                                   @RequestBody final WalletDto walletDto) throws WalletCreationLimitException {
        WalletDto createdWallet = userService.addWallet(userId, walletDto);
        return ResponseEntity.ok().body(createdWallet);
    }
}
