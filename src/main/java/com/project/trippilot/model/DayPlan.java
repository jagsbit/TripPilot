package com.project.trippilot.model;

import java.util.List;


public record DayPlan(
        int day,
        String date,
        List<String> activities
) {}
