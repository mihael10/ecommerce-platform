package com.ecommerce.userservice.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import com.ecommerce.userservice.util.JwtUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Mock
    private JwtUtil jwtUtil;

    private static final String MOCK_USERNAME = "testuser";
    private static final String MOCK_TOKEN = "mock-token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(exchange.getRequest()).thenReturn(request);
        when(exchange.getResponse()).thenReturn(response);
    }

    @Test
    void filter_ShouldProceedWithoutAuthHeader() {
        when(request.getHeaders()).thenReturn(new HttpHeaders());

        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, chain);

        assertNotNull(result);
        verify(chain, times(1)).filter(exchange);
    }

    @Test
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
        String token = "Bearer " + MOCK_TOKEN;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);

        when(request.getHeaders()).thenReturn(headers);
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(jwtUtil.extractUsername(MOCK_TOKEN)).thenReturn(MOCK_USERNAME);
        when(jwtUtil.validateToken(MOCK_TOKEN, MOCK_USERNAME)).thenReturn(true);

        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, chain);

        assertNotNull(result);
        verify(chain, times(1)).filter(exchange);
    }

    @Test
    void filter_ShouldRejectInvalidToken() {
        String token = "Bearer " + MOCK_TOKEN;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);

        when(request.getHeaders()).thenReturn(headers);
        when(jwtUtil.extractUsername(MOCK_TOKEN)).thenThrow(new IllegalArgumentException("bad token"));
        when(response.setComplete()).thenReturn(Mono.empty());

        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, chain);

        assertNotNull(result);
        verify(chain, never()).filter(exchange);
        verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
    }
}
