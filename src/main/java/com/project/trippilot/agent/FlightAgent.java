package com.project.trippilot.agent;
import com.project.trippilot.dto.FlightResult;
import com.project.trippilot.model.TravelState;
import com.project.trippilot.tool.FlightTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class FlightAgent {

    private final ChatClient chatClient;
    private final FlightTools flightTools;

    public FlightAgent(ChatClient.Builder builder, FlightTools flightTools) {
        this.chatClient = builder.build();
        this.flightTools = flightTools;
    }

    public FlightResult searchFlights(TravelState state) {

        return chatClient.prompt()
                .system("""
                        You are the Flight Agent.

                        Your responsibility is to find suitable flights
                        based on the travel information provided.

                        Use the available flight search tools.

                        Return structured flight information.
                        """)
                .user("""
                        Travel request:
                        %s

                        Origin:
                        %s

                        Destination:
                        %s

                        Departure date:
                        %s
                        """.formatted(
                        state.getUserQuery(),
                        state.getOrigin(),
                        state.getDestination(),
                        state.getDepartureDate()
                ))
                .tools(flightTools)
                .call()
                .entity(FlightResult.class);
    }
}
