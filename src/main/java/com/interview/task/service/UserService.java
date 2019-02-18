package com.interview.task.service;

import com.interview.task.dto.UserDto;
import com.interview.task.dto.WalletDto;
import com.interview.task.exceptions.WalletCreationLimitException;
import com.interview.task.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    Boolean existsUserByEmail(String email);
    User saveNewUser(User userDto);
    UserDto updateUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
    void removeUser(Long userId);
    WalletDto addWallet(Long userId, WalletDto walletDto) throws WalletCreationLimitException;
    List<WalletDto> getAllUserWallets(Long userId);
}
