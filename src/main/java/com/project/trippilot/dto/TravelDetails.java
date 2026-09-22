package com.project.trippilot.dto;

import java.time.LocalDate;

public record TravelDetails(
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate,
        Integer travelers,
        String preferences
) {
}
