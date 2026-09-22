package com.project.trippilot.controller;

import com.project.trippilot.agent.TripOrchestrator;
import com.project.trippilot.dto.ChatRequest;
import com.project.trippilot.dto.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final TripOrchestrator tripOrchestrator;

    public AgentController(TripOrchestrator tripOrchestrator) {
        this.tripOrchestrator = tripOrchestrator;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return tripOrchestrator.execute(request.sessionId(), request.message());
    }
}
