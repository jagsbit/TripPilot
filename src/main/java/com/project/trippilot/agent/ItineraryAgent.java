package com.project.trippilot.agent;

import com.project.trippilot.model.Itinerary;
import com.project.trippilot.model.TravelState;
import com.project.trippilot.tool.TavilyTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class ItineraryAgent {

    private final ChatClient chatClient;
    private final TavilyTools tavilyTools;

    public ItineraryAgent(ChatClient.Builder builder, TavilyTools tavilyTools) {
        this.chatClient = builder.build();
        this.tavilyTools = tavilyTools;
    }

    public Itinerary createItinerary(TravelState state) {

        return chatClient.prompt()
                .system("""
                        You are the Itinerary Agent.

                        Create a practical day-wise travel itinerary.

                        Consider:
                        - trip duration
                        - destination
                        - traveler preferences
                        - selected hotel
                        - flight timings

                        Use location and search tools when required.
                        """)
                .user("""
                        Destination:
                        %s

                        Departure:
                        %s

                        Return:
                        %s

                        Flight information:
                        %s

                        Hotel information:
                        %s

                        Preferences:
                        %s
                        """.formatted(
                        state.getDestination(),
                        state.getDepartureDate(),
                        state.getReturnDate(),
                        state.getFlightResults(),
                        state.getHotelResults(),
                        state.getPreferences()
                ))
                .tools(tavilyTools)
                .call()
                .entity(Itinerary.class);
    }
}
