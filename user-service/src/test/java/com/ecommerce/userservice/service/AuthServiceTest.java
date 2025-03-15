package com.ecommerce.userservice.service;

import com.ecommerce.userservice.entity.Role;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldReturnToken_WhenNewUser() {
        // Given
        String username = "newuser";
        String password = "password123";
        Role role = Role.ROLE_USER;
        String encodedPassword = "encodedPassword";
        String expectedToken = "mocked-jwt-token";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(jwtUtil.generateToken(username)).thenReturn(expectedToken);

        // When
        String token = authService.register(username, password, role);

        // Then
        assertNotNull(token);
        assertEquals(expectedToken, token);

        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtUtil, times(1)).generateToken(username);
    }

    @Test
    void register_ShouldThrowException_WhenUserAlreadyExists() {
        // Given
        String username = "existinguser";
        String password = "password123";
        Role role = Role.ROLE_USER;

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.register(username, password, role);
        });

        assertEquals("User already exists", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        // Given
        String username = "validuser";
        String password = "password123";
        String storedPassword = "encodedPassword";
        String expectedToken = "mocked-jwt-token";
        User user = new User();
        user.setUsername(username);
        user.setPassword(storedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, storedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(username)).thenReturn(expectedToken);

        // When
        String token = authService.login(username, password);

        // Then
        assertNotNull(token);
        assertEquals(expectedToken, token);

        verify(jwtUtil, times(1)).generateToken(username);
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        // Given
        String username = "nonexistentuser";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(username, password);
        });

        assertEquals("Invalid credentials", exception.getMessage());

        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsIncorrect() {
        // Given
        String username = "validuser";
        String password = "wrongpassword";
        String storedPassword = "encodedPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword(storedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, storedPassword)).thenReturn(false);

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(username, password);
        });

        assertEquals("Invalid credentials", exception.getMessage());

        verify(jwtUtil, never()).generateToken(anyString());
    }
}