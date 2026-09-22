package com.project.trippilot.dto;

public record HotelResult(
        String name,
        String location,
        Double price,
        Double rating,
        String description
) {}
