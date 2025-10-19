package com.example.weeklyplanner.domain.model;

import java.time.LocalTime;

public class PlanEntry {
    private final Integer id;
    private final Integer taskId;
    private final Integer dayPlanId;
    private final Integer dayIndex; // 1..7
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Integer durationMinutes;

    public PlanEntry(Integer id, Integer taskId, Integer dayPlanId, Integer dayIndex, 
                    LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.taskId = taskId;
        this.dayPlanId = dayPlanId;
        this.dayIndex = dayIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = calculateDurationMinutes(startTime, endTime);
    }

    public Integer getId() {
        return id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public Integer getDayPlanId() {
        return dayPlanId;
    }

    public Integer getDayIndex() {
        return dayIndex;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    private Integer calculateDurationMinutes(LocalTime start, LocalTime end) {
        return (int) java.time.Duration.between(start, end).toMinutes();
    }
}
