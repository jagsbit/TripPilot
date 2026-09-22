package com.project.trippilot.tool;

import com.project.trippilot.service.FlightSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class FlightTools {

    private static final Logger log = LoggerFactory.getLogger(FlightTools.class);

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

        try {
            return flightSearchService.searchFlights(
                    origin,
                    destination
            );
        } catch (Exception e) {
            log.error("Flight search tool failed for origin='{}', destination='{}'", origin, destination, e);
            // Tool responses must be valid JSON for the Gemini API
            return """
                    {"error": "Flight search failed: %s"}
                    """.formatted(e.getMessage() == null
                            ? "unknown error"
                            : e.getMessage().replace("\"", "'"));
        }
    }
}
