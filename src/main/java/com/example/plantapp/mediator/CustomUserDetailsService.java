package com.example.plantapp.mediator;

import com.example.plantapp.entity.User;
import com.example.plantapp.foundation.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(extractRole(user.getRole()))
                .build();
    }

    private String extractRole(String role) {
        // ROLE_USER → USER (Spring сам добавляет ROLE_)
        if (role == null) return "USER";
        return role.replace("ROLE_", "");
    }
}