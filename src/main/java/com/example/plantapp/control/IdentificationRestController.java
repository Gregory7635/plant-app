package com.example.plantapp.control;

import com.example.plantapp.entity.Identification;
import com.example.plantapp.foundation.IdentificationRepository;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/identifications")
@Tag(name = "Identifications API")
public class IdentificationRestController {

    private final IdentificationRepository identificationRepository;

    public IdentificationRestController(
            IdentificationRepository identificationRepository
    ) {
        this.identificationRepository = identificationRepository;
    }

    @Operation(summary = "Получить всю историю определений")
    @GetMapping
    public List<Identification> getAll() {
        return identificationRepository.findAll();
    }

    @Operation(summary = "Получить историю определений по ID пользователя")
    @GetMapping("/user/{id}")
    public List<Identification> getByUser(
            @PathVariable Long id
    ) {

        return identificationRepository
                .findByUserId(id);
    }
}