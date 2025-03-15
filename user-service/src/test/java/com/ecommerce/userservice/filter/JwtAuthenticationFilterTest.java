package com.ecommerce.userservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {
    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private ServerHttpRequest request;

    @Mock
    private ServerHttpResponse response;

    @Mock
    private WebFilterChain chain;

    private static final String SECRET_KEY = "your-secure-secret-key-should-be-very-long";
    private static final String MOCK_USERNAME = "testuser";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(exchange.getRequest()).thenReturn(request);
        when(exchange.getResponse()).thenReturn(response);
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private String generateTestToken() {
        return Jwts.builder()
                .setSubject(MOCK_USERNAME)
                .signWith(getSigningKey())
                .compact();
    }


    void filter_ShouldProceedWithoutAuthHeader() {
        when(request.getHeaders()).thenReturn(new HttpHeaders());

        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, chain);

        assertNotNull(result);
        verify(chain, times(1)).filter(exchange);
    }

    void filter_ShouldProceedWithInvalidAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "InvalidToken");

        when(request.getHeaders()).thenReturn(headers);

        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, chain);

        assertNotNull(result);
        verify(chain, times(1)).filter(exchange);
    }

    @Test
    void filter_ShouldAuthenticateUserWithValidToken() {
        String token = "Bearer " + generateTestToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);

        when(request.getHeaders()).thenReturn(headers);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, chain);

        assertNotNull(result);
        verify(chain, times(1)).filter(exchange);
    }

    @Test
    void extractUsername_ShouldReturnUsername() {
        String token = generateTestToken();
        String username = extractClaim(token, Claims::getSubject);

        assertEquals(MOCK_USERNAME, username);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}