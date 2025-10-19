package com.example.weeklyplanner.domain.model;

import com.example.weeklyplanner.domain.enumeration.TaskPriority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleWeeklyPlanner implements Planner {

    @Override
    public WeeklyPlan plan(Backlog backlog, UserPreference userPreference) {
        // Create a new weekly plan for the current week
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int week = getWeekOfYear(today);
        
        WeeklyPlan weeklyPlan = new WeeklyPlan(new ISOWeek(year, week));
        initializeDayPlans(weeklyPlan);
        
        // Get available tasks (backlog only contains TODO tasks)
        List<Task> availableTasks = backlog.getTasks().stream()
                .filter(task -> task.getDurationMinutes() != null && task.getDurationMinutes() > 0)
                .collect(Collectors.toList());
        
        if (!availableTasks.isEmpty()) {
            // Sort tasks by priority and due date
            List<Task> sortedTasks = sortTasksByPriority(availableTasks);
            
            // Distribute tasks across the week
            distributeTasksAcrossWeek(weeklyPlan, sortedTasks, userPreference);
        }
        
        return weeklyPlan;
    }
    
    /**
     * Sort tasks by priority (HIGH first) and then by due date (earliest first)
     */
    private List<Task> sortTasksByPriority(List<Task> tasks) {
        return tasks.stream()
                .sorted((t1, t2) -> {
                    // First sort by priority
                    int priorityComparison = t2.getPriority().ordinal() - t1.getPriority().ordinal();
                    if (priorityComparison != 0) {
                        return priorityComparison;
                    }
                    
                    // Then sort by due date (earliest first)
                    if (t1.getDueDate() == null && t2.getDueDate() == null) {
                        return 0;
                    }
                    if (t1.getDueDate() == null) {
                        return 1; // null due dates go last
                    }
                    if (t2.getDueDate() == null) {
                        return -1;
                    }
                    return t1.getDueDate().compareTo(t2.getDueDate());
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Distribute tasks across the 7 days of the week
     */
    private void distributeTasksAcrossWeek(WeeklyPlan weeklyPlan, List<Task> tasks, UserPreference userPreference) {
        LocalTime workStart = userPreference.getDayStart();
        LocalTime workEnd = userPreference.getDayEnd();
        int totalWorkMinutes = (int) ChronoUnit.MINUTES.between(workStart, workEnd);
        if (totalWorkMinutes <= 0) {
            return;
        }
        
        // Calculate available work time per day (assuming 8 hours max)
        int maxWorkMinutesPerDay = Math.min(totalWorkMinutes, 8 * 60); // 8 hours max
        
        int taskIndex = 0;
        
        // Distribute tasks across each day
        for (int dayIndex = 0; dayIndex < 7 && taskIndex < tasks.size(); dayIndex++) {
            DayPlan dayPlan = weeklyPlan.getDayPlans().get(dayIndex);
            LocalTime currentTime = workStart;
            int remainingMinutes = maxWorkMinutesPerDay;
            
            // Add tasks to this day until we run out of time or tasks
            while (taskIndex < tasks.size() && remainingMinutes > 0) {
                Task task = tasks.get(taskIndex);
                int taskDuration = task.getDurationMinutes();
                
                // Check if task fits in remaining time
                if (taskDuration <= remainingMinutes) {
                    // Create a plan entry for this task
                    LocalTime endTime = currentTime.plusMinutes(taskDuration);
                    
                    // Check if task fits within work hours
                    if (endTime.isBefore(workEnd) || endTime.equals(workEnd)) {
                        PlanEntry planEntry = new PlanEntry(
                            null, // id will be generated when saved
                            task.getId(),
                            null, // dayPlanId will be set when saved
                            dayIndex + 1, // dayIndex is 1-based (Monday = 1)
                            currentTime,
                            endTime
                        );
                        
                        dayPlan.getTaskEntries().add(planEntry);
                        
                        // Update time and remaining minutes
                        currentTime = endTime;
                        remainingMinutes -= taskDuration;
                        taskIndex++;
                    } else {
                        // Task doesn't fit in remaining work time, move to next day
                        break;
                    }
                } else {
                    // Task is too long for remaining time, move to next day
                    break;
                }
            }
        }
    }
    
    /**
     * Get the week number of the year for a given date
     */
    private int getWeekOfYear(LocalDate date) {
        // Simple implementation - in a real application you might want to use
        // a more sophisticated week calculation that handles edge cases
        return date.getDayOfYear() / 7 + 1;
    }

    /**
     * Ensure the weekly plan has a day plan for each day of the week.
     */
    private void initializeDayPlans(WeeklyPlan weeklyPlan) {
        List<DayPlan> dayPlans = weeklyPlan.getDayPlans();
        if (!dayPlans.isEmpty()) {
            return;
        }
        for (Day day : Day.values()) {
            dayPlans.add(new DayPlan(day));
        }
    }
}
