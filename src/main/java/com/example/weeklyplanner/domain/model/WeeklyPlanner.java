package com.example.weeklyplanner.domain.model;

public class WeeklyPlanner {
    private final Backlog backlog;
    private final WeeklyCalendar weeklyCalendar;
    private final UserPreference userPreference;

    public WeeklyPlanner(Backlog backlog, WeeklyCalendar weeklyCalendar, UserPreference userPreference) {
        this.backlog = backlog;
        this.weeklyCalendar = weeklyCalendar;
        this.userPreference = userPreference;
    }

    public void planWeek() {
        // TODO: Implement the planning algorithm
        throw new UnsupportedOperationException("Not implemented");
    }
}
