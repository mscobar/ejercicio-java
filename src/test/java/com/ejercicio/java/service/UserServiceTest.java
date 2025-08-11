package com.ejercicio.java.service;

import com.ejercicio.java.dto.UserRequestDTO;
import com.ejercicio.java.dto.UserResponseDTO;
import com.ejercicio.java.entity.UserJava;
import com.ejercicio.java.repository.UserRepository;
import com.ejercicio.java.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        userService.emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        userService.passwordRegex = "^(?=.*[A-Z])(?=.*\\d).+$";
    }

    @Test
    void registerValid() {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Manuel Escobar");
        request.setEmail("manuel@escobar.com");
        request.setPassword("Manuel123");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(jwtUtil.generateToken(request.getEmail())).thenReturn("valid-token");

        UserResponseDTO response = userService.register(request);

        assertNotNull(response);
        assertEquals("valid-token", response.getToken());
        assertTrue(response.isActive());
        verify(userRepository).save(any(UserJava.class));
    }

    @Test
    void registerInvalidMail() {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Manuel Escobar");
        request.setEmail("invalid-email");
        request.setPassword("pass");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(request);
        });

        assertEquals("Formato de correo inválido!", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerInvalidPass() {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("John Doe");
        request.setEmail("manuel@escobar.com");
        request.setPassword("wrong");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(request);
        });

        assertEquals("Formato de contraseña inválido!", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerExistEmail() {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Manuel Escobar");
        request.setEmail("manuel@escobar.com");
        request.setPassword("Manuel123");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(new UserJava()));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(request);
        });

        assertEquals("El correo ya fue registrado!", ex.getMessage());
        verify(userRepository, never()).save(any());
    }
}

