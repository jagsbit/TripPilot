package com.project.trippilot.service;

import com.project.trippilot.dto.LocationInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FlightSearchService {

    private final LocationResolverService locationResolver;
    private final AviationstackService aviationstackService;

    public FlightSearchService(
            LocationResolverService locationResolver,
            AviationstackService aviationstackService) {

        this.locationResolver = locationResolver;
        this.aviationstackService = aviationstackService;
    }

    public String searchFlights(
            String origin,
            String destination) {



        // Resolve origin
        LocationInfo originInfo =
                locationResolver.resolve(origin);

        // Resolve destination
        LocationInfo destinationInfo =
                locationResolver.resolve(destination);

        // Call Aviationstack
        return aviationstackService.searchFlights(
                originInfo.iataCode(),
                destinationInfo.iataCode()
        );
    }
}