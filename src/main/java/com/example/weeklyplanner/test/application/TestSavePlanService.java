package com.example.weeklyplanner.test.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;

import com.example.weeklyplanner.application.PlanWeekService;
import com.example.weeklyplanner.application.SavePlanService;
import com.example.weeklyplanner.domain.enumeration.TaskPriority;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Backlog;
import com.example.weeklyplanner.domain.model.ISOWeek;
import com.example.weeklyplanner.domain.model.SimpleWeeklyPlanner;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.port.WeeklyPlanRepository;

public class TestSavePlanService {

    @Test
    public void testGenerateSaveAndRetrievePlan() {
        InMemoryWeeklyPlanRepository repository = new InMemoryWeeklyPlanRepository();
        SavePlanService savePlanService = new SavePlanService(repository);
        PlanWeekService planWeekService = new PlanWeekService(new SimpleWeeklyPlanner(), savePlanService);

        List<Task> tasks = Arrays.asList(
                new Task(null, "Design MVP", "Prepare initial design", TaskStatus.TODO, TaskPriority.HIGH, "Work",
                        LocalDate.now().plusDays(1), 90),
                new Task(null, "Prototype feature", "Build clickable mock", TaskStatus.TODO, TaskPriority.MEDIUM, "Work",
                        LocalDate.now().plusDays(2), 120),
                new Task(null, "Code review", "Review teammate PR", TaskStatus.TODO, TaskPriority.MEDIUM, "Work",
                        LocalDate.now().plusDays(3), 60));

        WeeklyPlan savedPlan = planWeekService.planWeek(new Backlog(tasks),
                new UserPreference(LocalTime.of(9, 0), LocalTime.of(17, 0)));

        Assert.assertNotNull("Planner should return a weekly plan", savedPlan);
        Assert.assertFalse("Plan entries should be created for backlog tasks", savedPlan.getPlanEntries().isEmpty());

        ISOWeek isoWeek = savedPlan.getIsoWeek();
        WeeklyPlan retrievedPlan = savePlanService.getWeeklyPlan(isoWeek);

        Assert.assertNotNull("Retrieved plan should not be null", retrievedPlan);
        Assert.assertEquals("Year should remain consistent", isoWeek.getYear(), retrievedPlan.getIsoWeek().getYear());
        Assert.assertEquals("Week should remain consistent", isoWeek.getWeek(), retrievedPlan.getIsoWeek().getWeek());
        Assert.assertFalse("Retrieved plan should contain scheduled entries", retrievedPlan.getPlanEntries().isEmpty());
    }

    private static class InMemoryWeeklyPlanRepository implements WeeklyPlanRepository {
        private final Map<String, WeeklyPlan> storage = new ConcurrentHashMap<>();

        @Override
        public List<WeeklyPlan> getAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public WeeklyPlan getOne(Integer id) {
            throw new UnsupportedOperationException("Not required for in-memory repository");
        }

        @Override
        public WeeklyPlan add(WeeklyPlan entity) {
            storage.put(key(entity.getIsoWeek()), entity);
            return entity;
        }

        @Override
        public WeeklyPlan update(WeeklyPlan entity) {
            storage.put(key(entity.getIsoWeek()), entity);
            return entity;
        }

        @Override
        public WeeklyPlan delete(Integer id) {
            throw new UnsupportedOperationException("Not required for in-memory repository");
        }

        @Override
        public List<WeeklyPlan> getPlansByYearAndWeek(Integer year, Integer week) {
            WeeklyPlan plan = storage.get(year + "-" + week);
            return plan == null ? new ArrayList<>() : new ArrayList<>(List.of(plan));
        }

        @Override
        public List<WeeklyPlan> getPlansByVersion(String version) {
            return new ArrayList<>();
        }

        private String key(ISOWeek isoWeek) {
            if (isoWeek == null) {
                throw new IllegalArgumentException("ISO week required to store plan");
            }
            return isoWeek.getYear() + "-" + isoWeek.getWeek();
        }
    }
}
