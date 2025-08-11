package com.ejercicio.java.repository;

import com.ejercicio.java.entity.UserJava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Manuel Escobar
 * @version 1.0.0
 * User Repository
 */
@Repository
public interface UserRepository extends JpaRepository<UserJava, UUID> {
    Optional<UserJava> findByEmail(String email);
}