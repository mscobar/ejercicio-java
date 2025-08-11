package com.ejercicio.java.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * @author Manuel Escobar
 * @version 1.0.0
 * User Entity
 */
@Setter
@Getter
@Entity
public class UserJava {

    @Id
    private UUID id;
    
    private String name;
    private String email;
    private String password;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phone> phones;

    public UserJava() {

    }

    public void setIsActive(boolean b) {
    }
}
