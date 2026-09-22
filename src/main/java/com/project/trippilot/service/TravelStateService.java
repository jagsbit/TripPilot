package com.project.trippilot.service;

import com.project.trippilot.model.TravelState;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TravelStateService {

    private final Map<String, TravelState> states =
            new ConcurrentHashMap<>();

    public TravelState createState(String sessionId, String userQuery) {

        TravelState state = new TravelState();

        state.setSessionId(sessionId);
        state.setUserQuery(userQuery);

        states.put(sessionId, state);

        return state;
    }

    public TravelState getState(String sessionId) {
        return states.get(sessionId);
    }

    public void updateState(String sessionId, TravelState state) {
        states.put(sessionId, state);
    }
}
