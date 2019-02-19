package com.interview.task.service;

import com.interview.task.dto.UserDto;
import com.interview.task.dto.WalletDto;
import com.interview.task.entity.User;
import com.interview.task.entity.Wallet;
import com.interview.task.enums.Message;
import com.interview.task.enums.Role;
import com.interview.task.exceptions.UserNotFoundException;
import com.interview.task.exceptions.WalletCreationLimitException;
import com.interview.task.mapper.UserMapper;
import com.interview.task.mapper.WalletMapper;
import com.interview.task.repository.UserRepository;
import com.interview.task.repository.WalletRepository;
import com.interview.task.utils.WalletCurrencyValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final WalletMapper walletMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final WalletRepository walletRepository,
                           final PasswordEncoder passwordEncoder,
                           final UserMapper userMapper,
                           final WalletMapper walletMapper) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.walletMapper = walletMapper;
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND.getMsgBody());
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsUserByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveNewUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    @Override
    public UserDto updateUser(final UserDto userDto) {
        if (!userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND.getMsgBody());
        }
        final User updated = userRepository.save(parseToUserEntity(userDto));
        return parseToUserDto(updated);
    }

    @Override
    public List<UserDto> getAllUsers() {
        final List<UserDto> userDtoList = new ArrayList<>();
        final List<User> users = userRepository.findAll();
        users.forEach(u -> userDtoList.add(
                new UserDto(
                        u.getUserId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getPassword(),
                        u.getRoles(),
                        u.getWallets())));
        return userDtoList;
    }

    @Override
    public UserDto getUserById(final Long userId) {
        final User user = userRepository.getOne(userId);
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles(),
                user.getWallets()
        );
    }

    @Transactional
    @Override
    public WalletDto addWallet(final Long userId, final WalletDto walletDto) throws WalletCreationLimitException {
        final List<WalletDto> userWallets = userRepository.getAllClientWallets(userId);
        WalletCurrencyValidatorUtil.checkAvailableCurrencyForCreation(walletDto.getCurrency(), userWallets);
        final Optional<User> user = userRepository.findById(userId);
        if (userWallets.size() < 3) {
            Wallet savedWallet = walletRepository.save(parseToWalletEntity(walletDto));
            User currentUser = user.get();
            currentUser.getWallets().add(savedWallet);
            userRepository.save(currentUser);
            return parseToWalletDto(savedWallet);
        } else {
            throw new WalletCreationLimitException(Message.WALLET_CREATION_LIMIT.getMsgBody());
        }
    }
    @Transactional
    @Override
    public List<WalletDto> getAllUserWallets(final Long userId) {
        return userRepository.getAllClientWallets(userId);
    }

    @Override
    public void removeUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    private User parseToUserEntity(final UserDto userDto) {
        return userMapper.toEntity(userDto);
    }

    private UserDto parseToUserDto(final User user) {
        return userMapper.toDto(user);
    }

    private Wallet parseToWalletEntity(final WalletDto walletDto) {
        return walletMapper.toEntity(walletDto);
    }

    private WalletDto parseToWalletDto(final Wallet wallet) {
        return walletMapper.toDto(wallet);
    }
}
