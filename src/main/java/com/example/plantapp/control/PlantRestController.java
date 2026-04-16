package com.example.plantapp.control;

import com.example.plantapp.control.dto.PlantDto;
import com.example.plantapp.entity.Plant;
import com.example.plantapp.foundation.PlantRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@Tag(name = "Plants API")
public class PlantRestController {

    private final PlantRepository plantRepository;

    public PlantRestController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @Operation(summary = "Получить все растения")
    @GetMapping
    public List<Plant> getAll() {
        return plantRepository.findAll();
    }

    @Operation(summary = "Получить растение по ID")
    @GetMapping("/{id}")
    public Plant getById(@PathVariable Long id) {
        return plantRepository.findById(id)
                .orElseThrow();
    }

    @Operation(summary = "Создать растение")
    @PostMapping
    public Plant create(@Valid @RequestBody PlantDto dto) {

        Plant plant = new Plant();

        plant.setName(dto.getName());
        plant.setDescription(dto.getDescription());
        plant.setWikipediaUrl(dto.getWikipediaUrl());

        return plantRepository.save(plant);
    }

    @Operation(summary = "Обновить растение")
    @PutMapping("/{id}")
    public Plant update(
            @PathVariable Long id,
            @Valid @RequestBody PlantDto dto
    ) {

        Plant plant = plantRepository.findById(id)
                .orElseThrow();

        plant.setName(dto.getName());
        plant.setDescription(dto.getDescription());
        plant.setWikipediaUrl(dto.getWikipediaUrl());

        return plantRepository.save(plant);
    }

    @Operation(summary = "Удалить растение")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        plantRepository.deleteById(id);
    }
}