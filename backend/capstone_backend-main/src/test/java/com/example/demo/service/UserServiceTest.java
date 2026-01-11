package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnUser_whenUsernameExists() {
        User user = new User();
        user.setUsername("admin");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        User result = userService.getCurrentUser("admin");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }

    @Test
    void shouldUpdateUserStatus() {
        User user = new User();
        user.setId("123");
        user.setActive(true);

        when(userRepository.findById("123"))
                .thenReturn(Optional.of(user));

        userService.updateUserStatus("123", false);

        assertFalse(user.isActive());
        verify(userRepository).save(user);
    }
    @Test
    void getCurrentUser_shouldThrowException_whenNotFound() {
        when(userRepository.findByUsername("x"))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,   // ✅ MUST be RuntimeException
                () -> userService.getCurrentUser("x")
        );
    }
    @Test
    void updateUserStatus_shouldDeactivateUser() {
        User user = new User();
        user.setActive(true);

        when(userRepository.findById("u1"))
                .thenReturn(Optional.of(user));

        userService.updateUserStatus("u1", false);

        assertFalse(user.isActive());
    }


}
