package com.ejercicio.java.security;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Manuel Escobar
 * @version 1.0.0
 * Jwt Util
 * */
@Component
public class JwtUtil {
    public String generateToken(String subject) {
        return UUID.randomUUID().toString();
    }
}

