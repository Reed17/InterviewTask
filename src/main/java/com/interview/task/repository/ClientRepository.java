package com.interview.task.repository;

import com.interview.task.dto.WalletDto;
import com.interview.task.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT " +
            "NEW com.interview.task.dto.WalletDto( " +
            "wal.walletId, wal.currency, wal.balance) " +
            "FROM Client cl " +
            "JOIN cl.wallets wal " +
            "WHERE cl.clientId = :clientId")
    List<WalletDto> getAllClientWallets(@Param("clientId") final Long clientId);
}
