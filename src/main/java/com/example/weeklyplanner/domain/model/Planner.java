package com.example.weeklyplanner.domain.model;

public interface Planner {
    public WeeklyPlan plan(Backlog backlog, UserPreference userPreference);
}
