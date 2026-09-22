package com.project.trippilot.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Itinerary {

    private List<DayPlan> days;
}
