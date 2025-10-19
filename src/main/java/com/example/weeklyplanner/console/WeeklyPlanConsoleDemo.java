package com.example.weeklyplanner.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.weeklyplanner.application.PlanWeekService;
import com.example.weeklyplanner.application.SavePlanService;
import com.example.weeklyplanner.domain.enumeration.TaskPriority;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Backlog;
import com.example.weeklyplanner.domain.model.DayPlan;
import com.example.weeklyplanner.domain.model.ISOWeek;
import com.example.weeklyplanner.domain.model.PlanEntry;
import com.example.weeklyplanner.domain.model.SimpleWeeklyPlanner;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.port.WeeklyPlanRepository;

public final class WeeklyPlanConsoleDemo {

    private WeeklyPlanConsoleDemo() {
    }

    public static void main(String[] args) {
        List<Task> tasks = List.of(
                new Task(1001, "Design MVP", "Prepare initial UX flow", TaskStatus.TODO, TaskPriority.HIGH, "Work",
                        LocalDate.now().plusDays(1), 120),
                new Task(1002, "Implement feature", "Build scheduling logic", TaskStatus.TODO, TaskPriority.HIGH,
                        "Development", LocalDate.now().plusDays(2), 180),
                new Task(1003, "Team sync", "Discuss blockers", TaskStatus.TODO, TaskPriority.MEDIUM, "Meetings",
                        LocalDate.now().plusDays(3), 60),
                new Task(1004, "Write documentation", "Capture planner decisions", TaskStatus.TODO, TaskPriority.MEDIUM,
                        "Documentation", LocalDate.now().plusDays(4), 90));

        Map<Integer, Task> tasksById = new HashMap<>();
        tasks.forEach(task -> tasksById.put(task.getId(), task));

        Backlog backlog = new Backlog(tasks);
        UserPreference preference = new UserPreference(LocalTime.of(9, 0), LocalTime.of(17, 0));

        SavePlanService savePlanService = new SavePlanService(new InMemoryWeeklyPlanRepository());
        PlanWeekService planWeekService = new PlanWeekService(new SimpleWeeklyPlanner(), savePlanService);

        WeeklyPlan weeklyPlan = planWeekService.planWeek(backlog, preference);

        printWeeklyPlan(weeklyPlan, tasksById);
    }

    private static void printWeeklyPlan(WeeklyPlan weeklyPlan, Map<Integer, Task> tasksById) {
        ISOWeek isoWeek = weeklyPlan.getIsoWeek();
        System.out.printf("Weekly plan for %d - week %02d%n", isoWeek.getYear(), isoWeek.getWeek());
        System.out.println("-------------------------------------");

        for (DayPlan dayPlan : weeklyPlan.getDayPlans()) {
            System.out.println(dayPlan.getDay());
            if (dayPlan.getTaskEntries().isEmpty()) {
                System.out.println("  -- no tasks scheduled --");
                continue;
            }

            for (PlanEntry entry : dayPlan.getTaskEntries()) {
                Task task = tasksById.get(entry.getTaskId());
                String taskLabel = task != null ? task.getName() : "Task #" + entry.getTaskId();
                System.out.printf("  %s - %s : %s%n",
                        formatTime(entry.getStartTime()),
                        formatTime(entry.getEndTime()),
                        taskLabel);
            }
            System.out.println();
        }
    }

    private static String formatTime(LocalTime time) {
        return time == null ? "--:--" : time.toString();
    }

    private static final class InMemoryWeeklyPlanRepository implements WeeklyPlanRepository {
        private final Map<String, WeeklyPlan> storage = new ConcurrentHashMap<>();

        @Override
        public List<WeeklyPlan> getAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public WeeklyPlan getOne(Integer id) {
            throw new UnsupportedOperationException("getOne not supported for console demo");
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
            throw new UnsupportedOperationException("delete not supported for console demo");
        }

        @Override
        public List<WeeklyPlan> getPlansByYearAndWeek(Integer year, Integer week) {
            WeeklyPlan plan = storage.get(year + "-" + week);
            return plan == null ? new ArrayList<>() : List.of(plan);
        }

        @Override
        public List<WeeklyPlan> getPlansByVersion(String version) {
            return new ArrayList<>();
        }

        private String key(ISOWeek isoWeek) {
            if (isoWeek == null) {
                throw new IllegalArgumentException("ISO week required");
            }
            return isoWeek.getYear() + "-" + isoWeek.getWeek();
        }
    }
}
