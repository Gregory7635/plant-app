package com.example.plantapp.mediator;

import com.example.plantapp.entity.User;
import com.example.plantapp.foundation.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Test
    void loadUserByUsername_ShouldReturnUser() {

        UserRepository repo = mock(UserRepository.class);

        User user = new User();
        user.setUsername("john");
        user.setPassword("123");
        user.setRole("ROLE_USER");

        when(repo.findByUsername("john")).thenReturn(user);

        CustomUserDetailsService service =
                new CustomUserDetailsService(repo);

        UserDetails details = service.loadUserByUsername("john");

        assertEquals("john", details.getUsername());
        assertEquals("123", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_ShouldThrowException() {

        UserRepository repo = mock(UserRepository.class);
        when(repo.findByUsername("x")).thenReturn(null);

        CustomUserDetailsService service =
                new CustomUserDetailsService(repo);

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("x"));
    }
}