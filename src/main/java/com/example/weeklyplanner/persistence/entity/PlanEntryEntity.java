package com.example.weeklyplanner.persistence.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "plan_entry")
public class PlanEntryEntity {
    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, index = true, foreign = true, foreignAutoRefresh = true)
    private TaskEntity task;

    @DatabaseField(canBeNull = false, index = true, foreign = true, foreignAutoRefresh = true)
    private WeeklyPlanEntity plan;

    @DatabaseField(canBeNull = false, index = true)
    private Integer dayIndex;

    @DatabaseField(canBeNull = false, index = true)
    private Integer durationMinutes;

    @DatabaseField(canBeNull = false, index = true)
    private Integer startHour;

    @DatabaseField(canBeNull = false, index = true)
    private Integer startMinute;

    @DatabaseField(canBeNull = false, index = true)
    private Integer endHour;

    @DatabaseField(canBeNull = false, index = true)
    private Integer endMinute;

    // Constructors
    public PlanEntryEntity() {
        // ORMLite requires a no-arg constructor
    }

    public PlanEntryEntity(TaskEntity task, WeeklyPlanEntity plan, Integer dayIndex, 
                          Integer durationMinutes, Integer startHour, Integer startMinute,
                          Integer endHour, Integer endMinute) {
        this.task = task;
        this.plan = plan;
        this.dayIndex = dayIndex;
        this.durationMinutes = durationMinutes;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public WeeklyPlanEntity getPlan() {
        return plan;
    }

    public void setPlan(WeeklyPlanEntity plan) {
        this.plan = plan;
    }

    public Integer getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(Integer dayIndex) {
        this.dayIndex = dayIndex;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }
}
