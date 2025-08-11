package com.ejercicio.java.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private boolean isActive;
}
