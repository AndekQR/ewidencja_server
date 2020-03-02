package com.example.ewidencja.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;

    public JwtSecurityConfigurer(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtTokenAuthenticationFilter filter = new JwtTokenAuthenticationFilter(jwtTokenProvider);
        builder.exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
