package com.springrent.rent_admin_backend.api.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@AllArgsConstructor
@Configuration
public class WebSecurityConfig {

    private JWTRequestFilter jwtRequestFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtRequestFilter, AuthorizationFilter.class)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/register", "/auth/login").permitAll()
                                .anyRequest().authenticated()
                );

        return httpSecurity.build();
    }
}
