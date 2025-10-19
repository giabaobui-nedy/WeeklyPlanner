package com.example.weeklyplanner.domain.model;

import java.util.List;


public class WeeklyCalendar {
    private final List<Task> tasks;

    public WeeklyCalendar(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
