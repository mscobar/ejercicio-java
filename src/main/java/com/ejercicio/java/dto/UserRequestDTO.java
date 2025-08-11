package com.ejercicio.java.dto;

import com.ejercicio.java.entity.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank
    private String name;

    @Email
    private String email;

    private String password;

    private List<Phone> phones;
}
