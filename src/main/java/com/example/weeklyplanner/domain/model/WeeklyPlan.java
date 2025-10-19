package com.example.weeklyplanner.domain.model;

import java.util.List;
import java.util.ArrayList;

public class WeeklyPlan {
    private final ISOWeek isoWeek;
    private final List<DayPlan> dayPlans;

    public WeeklyPlan(ISOWeek isoWeek) {
        this.isoWeek = isoWeek;
        this.dayPlans = new ArrayList<>();
    }

    public ISOWeek getIsoWeek() {
        return isoWeek;
    }

    public List<DayPlan> getDayPlans() {
        return dayPlans;
    }

    public List<PlanEntry> getPlanEntries() {
        List<PlanEntry> allEntries = new ArrayList<>();
        for (DayPlan dayPlan : dayPlans) {
            allEntries.addAll(dayPlan.getTaskEntries());
        }
        return allEntries;
    }
}
