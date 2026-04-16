package com.example.plantapp.control;

import com.example.plantapp.entity.Identification;
import com.example.plantapp.mediator.PlantService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/identify")
@Tag(name = "Identify API")
public class ApiIdentificationController {

    private final PlantService plantService;

    public ApiIdentificationController(PlantService plantService) {
        this.plantService = plantService;
    }

    @Operation(summary = "Определить растение")
    @PostMapping
    public Identification identify(
            @RequestParam("file") MultipartFile file
    ) {

        return plantService.identifyPlantAndSave(file);
    }
}