package com.project.trippilot.controller;

import com.project.trippilot.dto.FlightRequest;
import com.project.trippilot.service.FlightSearchService;
import com.project.trippilot.service.TavilyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tooltest")
public class ToolTestController {

    private final TavilyService tavilyService;
    private final FlightSearchService flightSearchService;

    public ToolTestController(TavilyService tavilyService, FlightSearchService flightSearchService) {
        this.tavilyService = tavilyService;
        this.flightSearchService = flightSearchService;
    }
    @PostMapping("/tavily")
    public ResponseEntity<String> getSearchResult(@RequestBody String query){
        String result=tavilyService.search(query);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/flight")
    public ResponseEntity<String > getFlightResult(@RequestBody FlightRequest request){
        String result=flightSearchService.searchFlights(request.origin(),request.destination());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
