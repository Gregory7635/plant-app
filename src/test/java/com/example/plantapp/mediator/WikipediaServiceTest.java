package com.example.plantapp.mediator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WikipediaServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    WikipediaService service;

    @Test
    void getWikipediaUrl_ShouldReturnCorrectUrl() {

        String result = service.getWikipediaUrl("Rose Flower");

        assertEquals(
                "https://ru.wikipedia.org/wiki/Rose_Flower",
                result
        );
    }

    @Test
    void getDescription_WhenRuApiReturnsData() {

        Map<String, Object> body = new HashMap<>();
        body.put("extract", "Beautiful flower");

        ResponseEntity<Map> responseEntity =
                ResponseEntity.ok(body);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(responseEntity);

        String result = service.getDescription("Rose");

        assertEquals("Beautiful flower", result);
    }

    @Test
    void getDescription_WhenEnFallbackReturnsData() {

        Map<String, Object> body = new HashMap<>();
        body.put("extract", "English description");

        ResponseEntity<Map> responseEntity =
                ResponseEntity.ok(body);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(
                ResponseEntity.ok(null),
                responseEntity
        );

        String result = service.getDescription("Rose");

        assertEquals("English description", result);
    }

    @Test
    void getDescription_WhenApiFails() {

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenThrow(new RuntimeException());

        String result = service.getDescription("Rose");

        assertEquals("Описание не найдено", result);
    }
}