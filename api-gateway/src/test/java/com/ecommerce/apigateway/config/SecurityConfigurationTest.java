package com.ecommerce.apigateway.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@Import(SecurityConfiguration.class)
class SecurityConfigurationTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveJwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    void whenAccessingEureka_thenPermitAll() {
        webTestClient.get()
                .uri("/eureka/some-endpoint")
                .exchange()
                .expectStatus().isOk(); // Expect open access
    }

    void whenAccessingAuthenticatedEndpoint_withValidJwt_thenAuthenticate() {
        // Mock the JwtDecoder to return a valid Jwt
        Jwt mockJwt = Jwt.withTokenValue("mock-token")
                .header("alg", "HS256")
                .claim("scope", "read")
                .build();
        when(jwtDecoder.decode(any(String.class))).thenReturn(Mono.just(mockJwt));

        webTestClient.get()
                .uri("/secure-endpoint")
                .headers(headers -> headers.setBearerAuth("mock-token"))
                .exchange()
                .expectStatus().isOk(); // Expect authenticated access
    }

    @Test
    void whenAccessingAuthenticatedEndpoint_withInvalidJwt_thenReturnUnauthorized() {
        // Mock the JwtDecoder to throw an exception for invalid tokens
        when(jwtDecoder.decode(any(String.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));

        webTestClient.get()
                .uri("/secure-endpoint")
                .headers(headers -> headers.setBearerAuth("invalid-token"))
                .exchange()
                .expectStatus().isUnauthorized(); // Expect unauthorized access
    }

    @Test
    void whenAccessingOtherEndpointsWithoutJwt_thenReturnUnauthorized() {
        webTestClient.get()
                .uri("/secure-endpoint")
                .exchange()
                .expectStatus().isUnauthorized(); // Expect unauthorized access
    }
}
