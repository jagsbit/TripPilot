package com.project.trippilot.dto;

public record LocationInfo(
        String originalInput,
        String city,
        String country,
        String airportName,
        String iataCode
) {
}
