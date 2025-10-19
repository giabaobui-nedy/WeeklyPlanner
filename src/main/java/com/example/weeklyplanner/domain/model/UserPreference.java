package com.example.weeklyplanner.domain.model;

import java.time.LocalTime;

public class UserPreference {
    private final LocalTime dayStart;
    private final LocalTime dayEnd;
    // TODO: Add breaks later

    public UserPreference(LocalTime dayStart, LocalTime dayEnd) {
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
    }

    public LocalTime getDayStart() {
        return dayStart;
    }

    public LocalTime getDayEnd() {
        return dayEnd;
    }
}
