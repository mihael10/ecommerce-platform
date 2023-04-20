package com.ecommerce.discoveryserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(enforceUniqueMethods = false)
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${eureka.username}")
    private String username;
    @Value("${eureka.password}")
    private String password;


    @Autowired
    public void  configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(username).password(password)
                .authorities("USER");
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests().anyRequest()
                .authenticated()
                .and()
                .httpBasic();
       return http.build();
    }
}
