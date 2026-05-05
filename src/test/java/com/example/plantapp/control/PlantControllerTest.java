package com.example.plantapp.control;

import com.example.plantapp.entity.Plant;
import com.example.plantapp.foundation.PlantRepository;
import com.example.plantapp.mediator.PlantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PlantController.class)
@AutoConfigureMockMvc(addFilters = false)
class PlantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantService plantService;

    @MockBean
    private PlantRepository plantRepository;

    @Test
    void index_ShouldReturnIndexPage() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void history_ShouldReturnHistoryPage() throws Exception {

        when(plantService.getHistoryForCurrentUser())
                .thenReturn(java.util.List.of());

        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("history"))
                .andExpect(model().attributeExists("history"));
    }

    @Test
    void plantCard_ShouldReturnPlantView() throws Exception {

        Plant plant = new Plant();
        plant.setId(1L);
        plant.setName("Rose");
        plant.setDescription("Beautiful flower");

        when(plantRepository.findById(1L))
                .thenReturn(Optional.of(plant));

        mockMvc.perform(get("/plants/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("plant-card"))
                .andExpect(model().attributeExists("plant"))
                .andExpect(model().attribute("plant", plant));
    }
}