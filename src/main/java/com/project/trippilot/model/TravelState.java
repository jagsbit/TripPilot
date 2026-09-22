package com.project.trippilot.model;

import com.project.trippilot.dto.FlightResult;
import com.project.trippilot.dto.HotelResult;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TravelState {

    private String sessionId;

    private String userQuery;

    private String origin;

    private String destination;

    private LocalDate departureDate;

    private LocalDate returnDate;

    private Integer travelers;

    private String preferences;

    private FlightResult flightResults;

    private List<HotelResult> hotelResults;

    private Itinerary itinerary;

    private String finalResponse;

    private List<String> messages = new ArrayList<>();
}