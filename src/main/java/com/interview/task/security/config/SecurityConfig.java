package com.interview.task.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // todo impl userDetailsService, jwt, passwordEncoder, jwt entry point
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // todo override
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // todo override
        super.configure(http);
    }
}
