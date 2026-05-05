package com.example.plantapp.mediator;

import com.example.plantapp.entity.Identification;
import com.example.plantapp.entity.Plant;
import com.example.plantapp.entity.User;
import com.example.plantapp.foundation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class PlantServiceIdentifyTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private IdentificationRepository identificationRepository;

    @Mock
    private WikipediaService wikipediaService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PlantService plantService;

    @Test
    void identifyPlantAndSave_ShouldReturnSavedIdentification() throws Exception {

        // =========================
        // FIX @Value fields (IMPORTANT)
        // =========================
        ReflectionTestUtils.setField(plantService, "apiUrl", "http://fake-api");
        ReflectionTestUtils.setField(plantService, "apiKey", "fake-key");

        // =========================
        // Security context
        // =========================
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("john", null, List.of())
        );

        // =========================
        // User mock
        // =========================
        User user = new User();
        user.setUsername("john");

        when(userRepository.findByUsername("john"))
                .thenReturn(user);

        // =========================
        // Wikipedia mocks
        // =========================
        when(wikipediaService.getDescription(anyString()))
                .thenReturn("desc");

        when(wikipediaService.getWikipediaUrl(anyString()))
                .thenReturn("url");

        // =========================
        // Plant mock
        // =========================
        Plant plant = new Plant();
        plant.setName("Rose");

        when(plantRepository.findByName(anyString()))
                .thenReturn(plant);

        // =========================
        // Save mock (return same object)
        // =========================
        when(identificationRepository.save(any(Identification.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // =========================
        // API response mock
        // =========================
        Map<String, Object> best = Map.of(
                "name", "Rose",
                "probability", 0.88
        );

        Map<String, Object> classification = Map.of(
                "suggestions", List.of(best)
        );

        Map<String, Object> result = Map.of(
                "classification", classification
        );

        Map<String, Object> body = Map.of("result", result);

        ResponseEntity<Map> response = ResponseEntity.ok(body);

        when(restTemplate.postForEntity(
                anyString(),
                any(),
                any(Class.class)
        )).thenReturn(response);

        // =========================
        // Input file
        // =========================
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "plant.jpg",
                "image/jpeg",
                "fake-image".getBytes()
        );

        // =========================
        // Execute
        // =========================
        Identification resultEntity =
                plantService.identifyPlantAndSave(file);

        // =========================
        // Assertions
        // =========================
        assertNotNull(resultEntity);
        assertEquals(plant, resultEntity.getPlant());
        assertEquals(user, resultEntity.getUser());
        assertEquals(0.88, resultEntity.getProbability());

        // =========================
        // Verify calls
        // =========================
        verify(userRepository).findByUsername("john");
        verify(plantRepository).findByName(anyString());
        verify(identificationRepository).save(any());
    }
}