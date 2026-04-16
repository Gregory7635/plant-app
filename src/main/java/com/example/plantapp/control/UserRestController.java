package com.example.plantapp.control;

import com.example.plantapp.entity.User;
import com.example.plantapp.foundation.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API")
public class UserRestController {

    private final UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "Получить информацию о текущем пользователе")
    @GetMapping("/me")
    public User currentUser(Authentication authentication) {

        return userRepository.findByUsername(
                authentication.getName()
        );
    }
}