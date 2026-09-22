package com.project.trippilot.agent;

import com.project.trippilot.dto.TravelDetails;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class TravelDetailsExtractor {

    private final ChatClient chatClient;

    public TravelDetailsExtractor(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public TravelDetails extract(String userQuery) {

        return chatClient.prompt()
                .system("""
                        Extract travel information from the user's request.

                        Extract:
                        - origin
                        - destination
                        - departure date
                        - return date
                        - number of travelers
                        - travel preferences

                        If information is not provided, return null.

                        Do not invent information.
                        """)
                .user(userQuery)
                .call()
                .entity(TravelDetails.class);
    }
}
