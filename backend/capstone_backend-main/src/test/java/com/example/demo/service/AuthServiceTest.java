package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.config.JwtUtil;
import com.example.demo.service.impl.AuthServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    // ✅ LOGIN SUCCESS
    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPass");
        user.setRole("ADMIN");

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("password");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPass"))
                .thenReturn(true);
        when(jwtUtil.generateToken("admin", "ADMIN"))
                .thenReturn("jwt-token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
    }

    // ❌ LOGIN FAIL – USER NOT FOUND
    @Test
    void login_shouldThrowException_whenUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("password");

        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );
    }

    // ❌ LOGIN FAIL – WRONG PASSWORD
    @Test
    void login_shouldThrowException_whenPasswordIsInvalid() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPass");

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("wrongPass");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPass", "encodedPass"))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );
    }

    @Test
    void register_shouldSaveUser_whenUsernameDoesNotExist() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newuser");
        request.setPassword("password");
        request.setRole("RM");

        when(userRepository.findByUsername("newuser"))
                .thenReturn(Optional.empty());

        authService.register(request);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_shouldAllowDuplicateUserRegistration() {
        User existingUser = new User();
        existingUser.setUsername("admin");

        RegisterRequest request = new RegisterRequest();
        request.setUsername("admin");
        request.setPassword("password");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(existingUser));

        assertDoesNotThrow(() -> authService.register(request));
    }

}
