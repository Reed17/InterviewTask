package com.interview.task.controller;

import com.interview.task.dto.ApiResponse;
import com.interview.task.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Class represents wallet controller.
 */
@RestController
@PreAuthorize("hasAnyAuthority('USER')")
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(final WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Method add balance to the wallet.
     *
     * @param walletId wallet id.
     * @param amount money amount
     * @return ResponseEntity
     */
    @PutMapping("/add/{walletId}")
    public ResponseEntity<?> addBalance(@PathVariable("walletId") final Long walletId,
                                        @RequestParam("amount") final Double amount) {
        walletService.add(walletId, amount);
        return ResponseEntity.ok().body(new ApiResponse(true, "Balance add operation successful!"));
    }

    /**
     * Method performs balance reducing for a certain wallet.
     *
     * @param walletId wallet id.
     * @param amount money amount
     * @return ResponseEntity
     */
    @PutMapping("/reduce/{walletId}")
    public ResponseEntity<?> reduceBalance(@PathVariable("walletId") final Long walletId,
                                           @RequestParam("amount") final Double amount) {
        walletService.subtract(walletId, amount);
        return ResponseEntity.ok().body(new ApiResponse(true, "Balance reduce operation successful!"));
    }

    /**
     * Method transfer money from one wallet to another.
     *
     * @param fromWalletId wallet id which balance will be reducing
     * @param toWalletId wallet id which balance will be replenished
     * @param amount money amount.
     * @return ResponseEntity
     */
    @PutMapping("/{fromId}/replenish/{toId}")
    public ResponseEntity<?> replenishUserBalance(@PathVariable("fromId") final Long fromWalletId,
                                                  @PathVariable("toId") final Long toWalletId,
                                                  @RequestParam("amount") final Double amount) {
        boolean isReplenished = walletService.replenishBalance(fromWalletId, toWalletId, amount);
        return ResponseEntity.ok().body(new ApiResponse(isReplenished, "Balance replenish operation successful!"));
    }

    /**
     * Method performs multicurrent money transfer beetween two wallets.
     *
     * @param fromWalletId wallet id which balance will be reducing
     * @param toWalletId wallet id which balance will be replenished
     * @param amount money  amount
     * @return ResponseEntity
     */
    @PutMapping("/{fromId}/multicurr/{toId}")
    public ResponseEntity<?> replenishBalanceMultiCurrency(@PathVariable("fromId") final Long fromWalletId,
                                                           @PathVariable("toId") final Long toWalletId,
                                                           @RequestParam("amount") final Double amount) {
        boolean isMultiCurrReplenished = walletService.replenishBalanceByDifferentCurrencies(fromWalletId, toWalletId, amount);
        return ResponseEntity.ok().body(
                new ApiResponse(
                        isMultiCurrReplenished,
                        "Multicurrence balance replenish operation successful!"));
    }

    /**
     * Method performs wallet deletion.
     *
     * @param walletId wallet id for deleting.
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWallet(@PathVariable("id") final Long walletId) {
        walletService.removeWallet(walletId);
        return ResponseEntity.ok().body(new ApiResponse(true, "Wallet was successfully removed!"));
    }
}
