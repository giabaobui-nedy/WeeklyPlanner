package com.example.weeklyplanner.domain.model;

import java.time.ZonedDateTime;

public class PlanEntry {
    private final Integer id;
    private final Integer taskId;
    private final Integer dayPlanId;
    private final Integer dayIndex; // 1..7
    private final ZonedDateTime startAt;
    private final ZonedDateTime endAt;

    public PlanEntry(Integer id, Integer taskId, Integer dayPlanId, Integer dayIndex, 
                    ZonedDateTime startAt, ZonedDateTime endAt) {
        this.id = id;
        this.taskId = taskId;
        this.dayPlanId = dayPlanId;
        this.dayIndex = dayIndex;
        this.startAt = startAt;
        this.endAt = endAt;
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

    public ZonedDateTime getStartAt() {
        return startAt;
    }

    public ZonedDateTime getEndAt() {
        return endAt;
    }
}
