package com.project.trippilot.agent;

import com.project.trippilot.repository.SessionRepository;
import com.project.trippilot.dto.ChatResponse;
import com.project.trippilot.dto.FlightResult;
import com.project.trippilot.dto.HotelResult;
import com.project.trippilot.dto.TravelDetails;
import com.project.trippilot.model.Itinerary;
import com.project.trippilot.model.TravelState;
import com.project.trippilot.service.TravelStateService;
import com.project.trippilot.session.TravelSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TripOrchestrator {

    private final FlightAgent flightAgent;
    private final HotelAgent hotelAgent;
    private final ItineraryAgent itineraryAgent;
    private final FinalResponseAgent finalResponseAgent;
    private final TravelStateService stateService;
    private final SessionRepository sessionRepository;
    private final TravelDetailsExtractor travelDetailsExtractor;

    public TripOrchestrator(
            FlightAgent flightAgent,
            HotelAgent hotelAgent,
            ItineraryAgent itineraryAgent,
            FinalResponseAgent finalResponseAgent,
            TravelStateService stateService, SessionRepository sessionRepository, TravelDetailsExtractor travelDetailsExtractor) {

        this.flightAgent = flightAgent;
        this.hotelAgent = hotelAgent;
        this.itineraryAgent = itineraryAgent;
        this.finalResponseAgent = finalResponseAgent;
        this.stateService = stateService;
        this.sessionRepository = sessionRepository;
        this.travelDetailsExtractor = travelDetailsExtractor;
    }

    public ChatResponse execute(String sessionId, String userQuery) {

        // 1. Create or reuse session
        if (sessionId == null || sessionId.isBlank()) {
            sessionId = UUID.randomUUID().toString();
            TravelSession session = new TravelSession();
            session.setSessionId(sessionId);
            session.setUserQuery(userQuery);
            sessionRepository.save(session);
        }

        // 2. Load existing state or create a new one
        TravelState state = stateService.getState(sessionId);
        if (state == null) {
            state = stateService.createState(sessionId, userQuery);
        } else {
            state.setUserQuery(userQuery);
        }

        // 3. Extract travel information
        extractTravelDetails(state);

        // 4. Flight Agent
        FlightResult flights =
                flightAgent.searchFlights(state);

        state.setFlightResults(flights);
        stateService.updateState(sessionId, state);

        // 5. Hotel Agent
        List<HotelResult> hotels =
                hotelAgent.searchHotels(state);

        state.setHotelResults(hotels);
        stateService.updateState(sessionId, state);

        // 6. Itinerary Agent
        Itinerary itinerary =
                itineraryAgent.createItinerary(state);

        state.setItinerary(itinerary);
        stateService.updateState(sessionId, state);

        // 7. Final Response Agent
        String response =
                finalResponseAgent.generateResponse(state);

        state.setFinalResponse(response);
        stateService.updateState(sessionId, state);

        return new ChatResponse(sessionId, response);
    }

    private void extractTravelDetails(TravelState state) {

        TravelDetails details =
                travelDetailsExtractor.extract(state.getUserQuery());

        state.setOrigin(details.origin());
        state.setDestination(details.destination());
        state.setDepartureDate(details.departureDate());
        state.setReturnDate(details.returnDate());
        state.setTravelers(details.travelers());
        state.setPreferences(details.preferences());
    }
}
