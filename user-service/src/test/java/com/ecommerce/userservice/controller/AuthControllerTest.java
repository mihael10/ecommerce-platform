package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.entity.Role;
import com.ecommerce.userservice.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldReturnToken_WhenValidInput() {
        // Given
        String username = "testuser";
        String password = "password123";
        Role role = Role.ROLE_USER;
        String expectedToken = "mocked-jwt-token";

        when(authService.register(username, password, role)).thenReturn(expectedToken);

        // When
        ResponseEntity<String> response = authController.register(username, password, role);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedToken, response.getBody());

        verify(authService, times(1)).register(username, password, role);
    }

    @Test
    void login_ShouldReturnToken_WhenValidInput() {
        // Given
        String username = "testuser";
        String password = "password123";
        String expectedToken = "mocked-jwt-token";

        when(authService.login(username, password)).thenReturn(expectedToken);

        // When
        ResponseEntity<String> response = authController.login(username, password);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedToken, response.getBody());

        verify(authService, times(1)).login(username, password);
    }
}