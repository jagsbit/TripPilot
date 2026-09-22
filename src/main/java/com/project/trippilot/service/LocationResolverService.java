package com.project.trippilot.service;
import com.project.trippilot.dto.LocationInfo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LocationResolverService {

    private final TavilyService tavilyService;
    private final ChatClient chatClient;

    public LocationResolverService(
            TavilyService tavilyService,
            ChatClient.Builder chatClientBuilder) {

        this.tavilyService = tavilyService;
        this.chatClient = chatClientBuilder.build();
    }

    public LocationInfo resolve(String location) {

        String tavilyResult = tavilyService.search(
                buildSearchQuery(location)
        );

        return chatClient.prompt()
                .system("""
                        You are an airport location resolver.

                        Given web search results, identify the most
                        appropriate commercial airport.

                        Rules:
                        1. If the input is a city, find an airport
                           serving that city.
                        2. If the city has no suitable airport,
                           find the nearest airport with scheduled
                           passenger flights.
                        3. If the input is a country, first determine
                           its capital and then find the main airport
                           serving that capital.
                        4. Return the IATA code.
                        5. Do not invent an IATA code.
                        """)
                .user("""
                        User location:
                        %s

                        Web search results:
                        %s
                        """.formatted(location, tavilyResult))
                .call()
                .entity(LocationInfo.class);
    }

    private String buildSearchQuery(String location) {

        return """
                Find the appropriate commercial passenger airport
                for "%s".

                Provide:
                - airport name
                - IATA code
                - city
                - country

                If this is a country, identify its capital first.

                If this is a city without a suitable commercial airport,
                identify the nearest airport with scheduled passenger
                flights.
                """.formatted(location);
    }
}