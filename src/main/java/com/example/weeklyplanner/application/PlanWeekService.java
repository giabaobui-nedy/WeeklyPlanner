package com.example.weeklyplanner.application;

import com.example.weeklyplanner.domain.model.SimpleWeeklyPlanner;
import com.example.weeklyplanner.domain.model.Backlog;
import com.example.weeklyplanner.domain.model.UserPreference;

public class PlanWeekService {
    private final SimpleWeeklyPlanner planner = new SimpleWeeklyPlanner();

    public void planWeek(Backlog backlog, UserPreference userPreference) {
        planner.plan(backlog, userPreference);
    }
}
