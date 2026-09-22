package com.project.trippilot.tool;

import com.project.trippilot.service.FlightSearchService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class FlightTools {

    private final FlightSearchService flightSearchService;

    public FlightTools(
            FlightSearchService flightSearchService) {

        this.flightSearchService = flightSearchService;
    }

    @Tool(
            description = """
        Search for flights between two locations.

        Locations can be:
        - airport IATA codes
        - airport names
        - cities
        - countries

        If a country is provided, resolve its capital and
        appropriate airport.

        If a city does not have a suitable commercial airport,
        find the nearest suitable airport.

        If departure date is not provided, use today's date.
        """
    )
    public String searchFlights(

            @ToolParam(
                    description = "Origin city, country, airport name or IATA code"
            )
            String origin,

            @ToolParam(
                    description = "Destination city, country, airport name or IATA code"
            )
            String destination ) {

        return flightSearchService.searchFlights(
                origin,
                destination
        );
    }
}
