package com.interview.task.security.service;

import com.interview.task.security.entity.User;
import com.interview.task.security.security.UserPrincipal;
import com.interview.task.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return UserPrincipal.create(user.get());
    }

    public UserDetails loadUserById(final Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return UserPrincipal.create(user.get());
    }
}
