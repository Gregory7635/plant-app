package com.example.plantapp.foundation;

import com.example.plantapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_ShouldReturnUser() {

        User user = new User();
        user.setUsername("john");
        user.setPassword("123");
        user.setRole("ROLE_USER");

        userRepository.save(user);

        User found =
                userRepository.findByUsername("john");

        assertNotNull(found);
        assertEquals("john", found.getUsername());
    }
}