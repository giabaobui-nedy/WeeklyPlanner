package com.example.weeklyplanner.application;

import com.example.weeklyplanner.domain.model.SimpleWeeklyPlanner;
import com.example.weeklyplanner.domain.model.Backlog;
import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.model.Planner;

public class PlanWeekService {
    private final Planner planner;
    private final SavePlanService savePlanService;

    public PlanWeekService() {
        this(new SimpleWeeklyPlanner(), new SavePlanService());
    }

    public PlanWeekService(Planner planner, SavePlanService savePlanService) {
        this.planner = planner;
        this.savePlanService = savePlanService;
    }

    public WeeklyPlan planWeek(Backlog backlog, UserPreference userPreference) {
        WeeklyPlan weeklyPlan = planner.plan(backlog, userPreference);
        return savePlanService.saveWeeklyPlan(weeklyPlan);
    }
}
