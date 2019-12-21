package com.wenx.resourceservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange()
                .pathMatchers("/hello/").permitAll()
                .pathMatchers("/**").authenticated()
                .anyExchange().authenticated()
                .and().httpBasic();
        http.oauth2ResourceServer().jwt();
        return http.build();
    }
}
