package com.example.weeklyplanner.test.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.example.weeklyplanner.application.PlanWeekService;
import com.example.weeklyplanner.domain.enumeration.TaskPriority;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Backlog;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.domain.model.WeeklyPlan;

public class TestPlanWeekService {
    @Test
    public void testPlanWeekService() {
        PlanWeekService planWeekService = new PlanWeekService();
        List<Task> tasks = Arrays.asList(
                new Task(null, "Prep interview", "Review requirements", TaskStatus.TODO, TaskPriority.HIGH, "Work",
                        LocalDate.now().plusDays(1), 120),
                new Task(null, "Write report", "Summarize weekly progress", TaskStatus.TODO, TaskPriority.MEDIUM,
                        "Work", LocalDate.now().plusDays(2), 90));
        Backlog backlog = new Backlog(tasks);
        UserPreference userPreference = new UserPreference(LocalTime.of(8, 0), LocalTime.of(18, 0));
        WeeklyPlan weeklyPlan = planWeekService.planWeek(backlog, userPreference);
        Assert.assertEquals("Expected a day plan for each day of the week", 7, weeklyPlan.getDayPlans().size());
        Assert.assertFalse("Planner should schedule backlog tasks", weeklyPlan.getPlanEntries().isEmpty());
        Assert.assertTrue("All tasks should be scheduled", weeklyPlan.getPlanEntries().size() >= tasks.size());
    }
}
