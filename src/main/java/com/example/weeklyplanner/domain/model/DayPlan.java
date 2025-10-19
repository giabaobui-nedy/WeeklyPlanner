package com.example.weeklyplanner.domain.model;

import java.util.List;
import java.util.ArrayList;

public class DayPlan {
    private final Day day;
    private final List<PlanEntry> taskEntries;

    public DayPlan(Day day) {
        this.day = day;
        this.taskEntries = new ArrayList<>();
    }

    public Day getDay() {
        return day;
    }

    public List<PlanEntry> getTaskEntries() {
        return taskEntries;
    }

    public Boolean addTaskEntry(PlanEntry taskEntry) {
        return taskEntries.add(taskEntry);
    }
}
