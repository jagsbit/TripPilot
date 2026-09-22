package com.project.trippilot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Service
public class AviationstackService {

    private final RestTemplate restTemplate;
    private final String apiKey;

    public AviationstackService(
            RestTemplate restTemplate,
            @Value("${flight.api.key}") String apiKey) {

        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public String searchFlights(
            String departureIata,
            String arrivalIata) {

        String url = UriComponentsBuilder
                .fromUriString("https://api.aviationstack.com/v1/flights")
                .queryParam("access_key", apiKey)
                .queryParam("dep_iata", departureIata)
                .queryParam("arr_iata", arrivalIata)
                .toUriString();

        ResponseEntity<String> response =
                restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }
}