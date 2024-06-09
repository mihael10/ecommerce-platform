package com.ecommerce.apigateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.web.server.SecurityWebFilterChain;

class SecurityConfigurationTest {
    SecurityConfiguration securityConfiguration = new SecurityConfiguration();


    void testWebFilterChain() {
        SecurityWebFilterChain result = securityConfiguration.webFilterChain(null);
        Assertions.assertEquals(null, result);
    }
}
