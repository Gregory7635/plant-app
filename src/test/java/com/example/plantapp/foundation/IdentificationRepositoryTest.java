package com.example.plantapp.foundation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class IdentificationRepositoryTest {

    @Autowired
    private IdentificationRepository repo;

    @Test
    void repoShouldLoad() {
        assertNotNull(repo);
    }
}