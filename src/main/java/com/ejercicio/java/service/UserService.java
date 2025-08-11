package com.ejercicio.java.service;

import com.ejercicio.java.dto.UserRequestDTO;
import com.ejercicio.java.dto.UserResponseDTO;
import com.ejercicio.java.entity.UserJava;
import com.ejercicio.java.repository.UserRepository;
import com.ejercicio.java.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.UUID;

/**
 * @author Manuel Escobar
 * @version 1.0.0
 * User Service
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${app.regex.email}")
    String emailRegex;

    @Value("${app.regex.password}")
    String passwordRegex;

    /**
     * Registra nuevo usuario
     * @return User
     */
    public UserResponseDTO register(UserRequestDTO request) {
        validateEmail(request.getEmail());
        validatePassword(request.getPassword());
        uniqueEmail(request.getEmail());

        return buildResponseFromUser(request);
    }

    /**
     * Validación email
     * @param email
     */
    private void validateEmail(String email){
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Formato de correo inválido!");
        }
    }

    /**
     * Validación password (poseer Mayusculas y números)
     * @param password
     */
    private void validatePassword(String password){
        if (!password.matches(passwordRegex)) {
            throw new IllegalArgumentException("Formato de contraseña inválido!");
        }
    }

    /**
     * Validación email duplicado
     * @param email
     */
    private void uniqueEmail(String email){
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new IllegalArgumentException("El correo ya fue registrado!");
        });
    }

    /**
     * Setear datos para el response
     * @param request
     * @return
     */
    private UserResponseDTO buildResponseFromUser(UserRequestDTO request) {
        UserJava user = new UserJava();
        user.setId(UUID.randomUUID());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhones(request.getPhones());
        user.setCreated(dateFormatter(LocalDateTime.now()));
        user.setModified(dateFormatter(LocalDateTime.now()));
        user.setLastLogin(dateFormatter(LocalDateTime.now()));
        user.setToken(jwtUtil.generateToken(request.getEmail()));
        user.setIsActive(true);

        userRepository.save(user);

        return new UserResponseDTO(user.getId(), user.getCreated(), user.getModified(),
                user.getLastLogin(), user.getToken(), user.isActive());
    }

    /**
     * Transforma fecha a dia/mes/año
     * @param date
     * @return
     */
    private String dateFormatter(LocalDateTime date){
        String input = date.toString();
        DateTimeFormatter parser = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                .toFormatter();
        LocalDateTime dateTime = LocalDateTime.parse(input, parser);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return dateTime.format(formatter);
    }
}

