package com.interview.task.service;

import com.interview.task.config.HeaderProperties;
import com.interview.task.config.JwtProperties;
import com.interview.task.dto.JwtAuthenticationResponse;
import com.interview.task.dto.LoginRequest;
import com.interview.task.dto.SignUpRequest;
import com.interview.task.entity.User;
import com.interview.task.exceptions.UserAlreadyExistsException;
import com.interview.task.security.JwtProvider;
import com.interview.task.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtProperties jwtProperties;
    private final HeaderProperties headerProperties;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthenticationServiceImpl(
            JwtProperties jwtProperties,
            HeaderProperties headerProperties,
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtProvider jwtProvider) {
        this.jwtProperties = jwtProperties;
        this.headerProperties = headerProperties;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JwtAuthenticationResponse signIn(final LoginRequest loginRequest, final HttpServletResponse response) {
        final Optional<User> user = userService.getUserByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(String.format("User with email %s doesn't exists!", loginRequest.getEmail()));
        }
        final User currentUser = user.get();
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        final String accessToken = jwtProvider.generateAccessToken(principal);
        response.setHeader(headerProperties.getName(), headerProperties.getType() + accessToken);
        return new JwtAuthenticationResponse(
                accessToken,
                headerProperties.getType().trim(),
                jwtProperties.getExpirationTimeMs(),
                LocalDate.now(),
                currentUser.getRoles());
    }

    @Transactional
    @Override
    public JwtAuthenticationResponse signUp(final SignUpRequest signUpRequest, final HttpServletResponse response) {
        if (userService.existsUserByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException(String.format("Email %s already exists", signUpRequest.getEmail()));
        }
        final User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getWallets());
        userService.saveNewUser(user);
        final User newUser = userService.getUserByEmail(signUpRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email %s doesn't exists!", signUpRequest.getEmail())));
        final String accessToken = jwtProvider.generateAccessToken(UserPrincipal.create(newUser));
        response.setHeader(headerProperties.getName(), headerProperties.getType() + accessToken);
        return new JwtAuthenticationResponse(
                accessToken,
                headerProperties.getType().trim(),
                jwtProperties.getExpirationTimeMs(),
                LocalDate.now(),
                newUser.getRoles());
    }
}
