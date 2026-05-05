package com.example.plantapp.control;

import com.example.plantapp.entity.Plant;
import com.example.plantapp.foundation.PlantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlantRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PlantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantRepository plantRepository;

    @Test
    void getAll_ShouldReturnPlants() throws Exception {

        Plant plant = new Plant();

        plant.setId(1L);
        plant.setName("Rose");

        when(plantRepository.findAll())
                .thenReturn(List.of(plant));

        mockMvc.perform(get("/api/plants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Rose"));
    }

    @Test
    void getById_ShouldReturnPlant() throws Exception {

        Plant plant = new Plant();

        plant.setId(1L);
        plant.setName("Rose");

        when(plantRepository.findById(1L))
                .thenReturn(Optional.of(plant));

        mockMvc.perform(get("/api/plants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Rose"));
    }
}