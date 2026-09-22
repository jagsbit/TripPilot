package com.project.trippilot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TavilyService {
    private final RestTemplate restTemplate;

    @Value("${tavily.api.key}")
    private String tavilyApiKey;

    public TavilyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String search(String query) {

        String url = "https://api.tavily.com/search";

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tavilyApiKey);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);

        // API call
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        return response.getBody();
    }
}
