package com.ecommerce.discoveryserver.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @InjectMocks
    private SecurityConfiguration securityConfiguration;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private InMemoryUserDetailsManagerConfigurer inMemoryUserDetailsManagerConfigurer;

    @BeforeEach
    void setUp() {
        securityConfiguration = new SecurityConfiguration();
    }

    @Test
    void testConfigureAuthentication() throws Exception {
        when(authenticationManagerBuilder.inMemoryAuthentication()).thenReturn(inMemoryUserDetailsManagerConfigurer);
        when(inMemoryUserDetailsManagerConfigurer.withUser(anyString())).thenReturn(inMemoryUserDetailsManagerConfigurer);
        when(inMemoryUserDetailsManagerConfigurer.password(anyString())).thenReturn(inMemoryUserDetailsManagerConfigurer);
        when(inMemoryUserDetailsManagerConfigurer.authorities(anyString())).thenReturn(inMemoryUserDetailsManagerConfigurer);

        securityConfiguration.configure(authenticationManagerBuilder);

        verify(authenticationManagerBuilder).inMemoryAuthentication();
        verify(inMemoryUserDetailsManagerConfigurer).withUser("admin");
        verify(inMemoryUserDetailsManagerConfigurer).password("password");
        verify(inMemoryUserIndiaDetailsManagerConfigurer).authorities("USER");
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        when(httpSecurity.csrf()).thenReturn(mock(HttpSecurity.CsrfConfigurer.class));
        when(httpSecurity.authorizeHttpRequests()).thenReturn(mock(HttpSecurity.AuthorizedUrl.class));
        when(httpSecurity.httpBasic()).thenReturn(mock(HttpSecurity.HttpBasicConfigurer.class));
        when(httpSecurity.anyRequest()).thenReturn(mock(HttpSecurity.AuthorizedUrl.class));
        when(httpSecurity.authenticated()).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(mock(SecurityFilterChain.class));

        SecurityFilterChain result = securityConfiguration.securityFilterChain(httpSecurity);

        Assertions.assertNotNull(result);
        verify(httpSecurity, times(1)).csrf().disable();
        verify(httpSecurity, times(1)).authorizeHttpRequests().anyRequest().authenticated();
        verify(httpSecurity, times(1)).httpBasic();
        verify(httpSecurity, times(1)).build();
    }
}
