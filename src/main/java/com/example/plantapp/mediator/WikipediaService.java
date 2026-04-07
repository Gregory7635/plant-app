package com.example.plantapp.mediator;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class WikipediaService {

    private final RestTemplate restTemplate;

    public WikipediaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWikipediaUrl(String plantName) {
        return "https://ru.wikipedia.org/wiki/" +
                plantName.replace(" ", "_");
    }

    public String getDescription(String plantName) {
        try {
            String encodedName = plantName.replace(" ", "_");

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "plant-app (test@example.com)");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // ===== RU =====
            String urlRu = "https://ru.wikipedia.org/api/rest_v1/page/summary/" + encodedName;

            ResponseEntity<Map> response = restTemplate.exchange(
                    urlRu,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            if (response.getBody() != null && response.getBody().get("extract") != null) {
                return trim((String) response.getBody().get("extract"));
            }

            // ===== EN fallback =====
            String urlEn = "https://en.wikipedia.org/api/rest_v1/page/summary/" + encodedName;

            response = restTemplate.exchange(
                    urlEn,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            if (response.getBody() != null && response.getBody().get("extract") != null) {
                return trim((String) response.getBody().get("extract"));
            }

            return "Описание не найдено";

        } catch (Exception e) {
            return "Описание не найдено";
        }
    }

    private String trim(String text) {
        // ограничим длину описания
        if (text.length() > 500) {
            return text.substring(0, 500) + "...";
        }
        return text;
    }
}