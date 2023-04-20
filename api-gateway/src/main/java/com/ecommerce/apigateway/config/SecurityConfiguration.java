package com.ecommerce.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
            http.csrf().disable()
                    .authorizeExchange(authorizeExchangeSpec ->
                            authorizeExchangeSpec.pathMatchers("/eureka/**")
                                    .permitAll()
                                    .anyExchange()
                                    .authenticated())
                    .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
           return http.build();
    }
}
