package com.example.plantapp.mediator;

import com.example.plantapp.entity.*;
import com.example.plantapp.foundation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PlantService {

    private final RestTemplate restTemplate;
    private final WikipediaService wikipediaService;
    private final UserRepository userRepository;

    @Value("${plant.id.api.key}")
    private String apiKey;

    @Value("${plant.id.url}")
    private String apiUrl;

    private final PlantRepository plantRepository;
    private final IdentificationRepository identificationRepository;

    public PlantService(RestTemplate restTemplate,
                        PlantRepository plantRepository,
                        IdentificationRepository identificationRepository,
                        WikipediaService wikipediaService,
                        UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.plantRepository = plantRepository;
        this.identificationRepository = identificationRepository;
        this.wikipediaService = wikipediaService;
        this.userRepository = userRepository;
    }

    public Identification identifyPlantAndSave(MultipartFile file) {
        try {
            // === API ВЫЗОВ (как у тебя уже есть) ===
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            Map<String, Object> body = new HashMap<>();
            body.put("images", List.of(base64Image));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Api-Key", apiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

            Map result = (Map) response.getBody().get("result");
            Map classification = (Map) result.get("classification");
            List suggestions = (List) classification.get("suggestions");

            Map best = (Map) suggestions.get(0);

            String name = (String) best.get("name");
            Double probability = (Double) best.get("probability");
            String description = wikipediaService.getDescription(name);
            String wikiUrl = wikipediaService.getWikipediaUrl(name);
            String username = getCurrentUsername();
            User user = userRepository.findByUsername(username);

            // === СОХРАНЕНИЕ PLANT ===
            Plant plant = plantRepository.findByName(name);
            if (plant == null) {
                plant = new Plant();
                plant.setName(name);
                plant.setDescription(description);
                plant.setWikipediaUrl(wikiUrl);

                plant = plantRepository.save(plant);
            }

            // === СОХРАНЕНИЕ IDENTIFICATION ===
            Identification identification = new Identification();
            identification.setPlant(plant);
            identification.setProbability(probability);
            identification.setCreatedAt(LocalDateTime.now());
            identification.setUser(user);

            return identificationRepository.save(identification);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Identification> getHistoryForCurrentUser() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username);

        return identificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}