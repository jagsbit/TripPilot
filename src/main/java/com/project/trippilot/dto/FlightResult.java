package com.project.trippilot.dto;
public record FlightResult(
        String flightNumber,
        String airline,
        String departureAirport,
        String arrivalAirport,
        String departureTime,
        String arrivalTime,
        String status
) {
}
