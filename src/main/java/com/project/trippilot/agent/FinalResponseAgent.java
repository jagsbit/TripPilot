package com.project.trippilot.agent;

import com.project.trippilot.model.TravelState;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class FinalResponseAgent {

    private final ChatClient chatClient;

    public FinalResponseAgent(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String generateResponse(TravelState state) {

        return chatClient.prompt()
                .system("""
                        You are the Final Response Agent.

                        Combine the information available in the travel state
                        into a clear and useful response.

                        Do not invent information.
                        If information is unavailable, clearly say so.

                        Organize the response using appropriate sections.
                        """)
                .user("""
                        User request:
                        %s

                        Flights:
                        %s

                        Hotels:
                        %s

                        Itinerary:
                        %s
                        """.formatted(
                        state.getUserQuery(),
                        state.getFlightResults(),
                        state.getHotelResults(),
                        state.getItinerary()
                ))
                .call()
                .content();
    }
}
