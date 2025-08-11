package com.ejercicio.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manuel Escobar
 * @version 1.0.0
 * Phone Entity
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id @GeneratedValue
    private Long id;
    private String number;
    private String cityCode;
    private String countryCode;
}