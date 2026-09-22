package com.project.trippilot.agent;

import com.project.trippilot.dto.HotelResult;
import com.project.trippilot.model.TravelState;
import com.project.trippilot.tool.TavilyTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelAgent {

    private final ChatClient chatClient;
    private final TavilyTools tavilyTools;

    public HotelAgent(ChatClient.Builder builder, TavilyTools tavilyTools) {
        this.chatClient = builder.build();
        this.tavilyTools = tavilyTools;
    }

    public List<HotelResult> searchHotels(TravelState state) {

        return chatClient.prompt()
                .system("""
                        You are the Hotel Agent.

                        Find suitable hotels based on the destination,
                        travel dates, number of travelers and preferences.

                        Use the available hotel search tools.
                        """)
                .user("""
                        Destination: %s
                        Check-in: %s
                        Check-out: %s
                        Travelers: %s
                        Preferences: %s
                        """.formatted(
                        state.getDestination(),
                        state.getDepartureDate(),
                        state.getReturnDate(),
                        state.getTravelers(),
                        state.getPreferences()
                ))
                .tools(tavilyTools)
                .call()
                .entity(new ParameterizedTypeReference<List<HotelResult>>() {
                });
    }
}
