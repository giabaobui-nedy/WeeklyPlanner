package com.example.weeklyplanner.test.application;

import java.time.LocalTime;

import org.junit.Test;

import com.example.weeklyplanner.application.PlanWeekService;
import com.example.weeklyplanner.domain.model.Backlog;
import com.example.weeklyplanner.domain.model.UserPreference;

public class TestPlanWeekService {
    @Test
    public void testPlanWeekService() {
        PlanWeekService planWeekService = new PlanWeekService();
        planWeekService.planWeek(new Backlog(), new UserPreference(LocalTime.of(8, 0), LocalTime.of(18, 0)));
    }
}
