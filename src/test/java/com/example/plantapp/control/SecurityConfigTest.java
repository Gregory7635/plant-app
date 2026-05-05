package com.example.plantapp.control;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    @Test
    void passwordEncoder_ShouldBeBCrypt() {

        SecurityConfig config = new SecurityConfig();

        PasswordEncoder encoder = config.passwordEncoder();

        assertNotNull(encoder);

        String encoded = encoder.encode("123");

        assertTrue(encoder.matches("123", encoded));
    }
}