package com.interview.task.security;

import com.interview.task.entity.User;
import com.interview.task.repository.UserRepository;
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
    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByEmail(email);
        return UserPrincipal.create(user.get());
    }

    public UserDetails loadUserById(final Long userId) {
        final Optional<User> user = userRepository.findById(userId);
        return UserPrincipal.create(user.get());
    }
}
