package com.example.plantapp.mediator;

import com.example.plantapp.entity.User;
import com.example.plantapp.foundation.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    void register_ShouldSaveEncodedPassword() {

        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        when(passwordEncoder.encode("1234"))
                .thenReturn("ENCODED");

        UserService userService =
                new UserService(userRepository, passwordEncoder);

        userService.register("alex", "1234");

        ArgumentCaptor<User> captor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();

        assertEquals("alex", savedUser.getUsername());
        assertEquals("ENCODED", savedUser.getPassword());
        assertEquals("ROLE_USER", savedUser.getRole());
    }
}