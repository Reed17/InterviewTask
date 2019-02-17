package com.interview.task.security.security;

import com.interview.task.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtEntryPointUnauthorizedHandler jwtUnauthorizedHandler;

    @Autowired
    public JwtAuthenticationFilter(final JwtProvider jwtProvider, final UserDetailsServiceImpl userDetailsService, final JwtEntryPointUnauthorizedHandler jwtUnauthorizedHandler) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.jwtUnauthorizedHandler = jwtUnauthorizedHandler;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = jwtProvider.getTokenFromRequest(request);
        try {
            if (StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken)) {
                final Long userId = jwtProvider.getUserIdFromToken(accessToken);
                final UserDetails userDetails = userDetailsService.loadUserById(userId);
                final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            jwtUnauthorizedHandler.commence(request, response, ex);
        }
        filterChain.doFilter(request, response);
    }
}
