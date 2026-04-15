package com.example.plantapp.control;

import com.example.plantapp.entity.Identification;
import com.example.plantapp.entity.Plant;
import com.example.plantapp.foundation.PlantRepository;
import com.example.plantapp.mediator.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.util.*;

@Controller
public class PlantController {

    private final PlantRepository plantRepository;

    @Autowired
    private PlantService plantService;

    public PlantController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @PostMapping("/identify")
    public String identify(@RequestParam("file") MultipartFile file, Model model) {
        Identification identification = plantService.identifyPlantAndSave(file);
        model.addAttribute("identification", identification);
        return "result";
    }

    @GetMapping("/history")
    public String history(Model model) {

        List<Identification> history = plantService.getHistoryForCurrentUser();

        model.addAttribute("history", history);

        return "history";
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Plant> latestPlants =
                plantRepository.findTop10ByOrderByCreatedAtDesc();

        model.addAttribute("latestPlants", latestPlants);

        return "index";
    }

    @GetMapping("/plants/{id}")
    public String plantCard(@PathVariable Long id, Model model) {

        Plant plant = plantRepository.findById(id)
                .orElseThrow();

        model.addAttribute("plant", plant);

        return "plant-card";
    }
}