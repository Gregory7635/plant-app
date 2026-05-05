package com.example.plantapp.mediator;

import com.example.plantapp.entity.*;
import com.example.plantapp.foundation.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(addFilters = false)
class PlantServiceTest {
    @Test
    void getHistory_ShouldReturnList() {

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("john", null, List.of())
        );

        PlantRepository plantRepository = mock(PlantRepository.class);
        IdentificationRepository identificationRepository = mock(IdentificationRepository.class);
        WikipediaService wikipediaService = mock(WikipediaService.class);
        UserRepository userRepository = mock(UserRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);

        User user = new User();
        user.setUsername("john");

        Identification id = new Identification();

        when(userRepository.findByUsername("john"))
                .thenReturn(user);

        when(identificationRepository.findByUserOrderByCreatedAtDesc(user))
                .thenReturn(List.of(id));

        PlantService service = new PlantService(
                restTemplate,
                plantRepository,
                identificationRepository,
                wikipediaService,
                userRepository
        );

        List<Identification> result = service.getHistoryForCurrentUser();

        assertEquals(1, result.size());
    }
}