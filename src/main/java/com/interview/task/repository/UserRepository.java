package com.interview.task.repository;

import com.interview.task.dto.WalletDto;
import com.interview.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT " +
            "NEW com.interview.task.dto.WalletDto( " +
            "wal.walletId, wal.currency, wal.balance) " +
            "FROM User u " +
            "JOIN u.wallets wal " +
            "WHERE u.userId = :userId")
    List<WalletDto> getAllClientWallets(@Param("userId") final Long userId);
}
