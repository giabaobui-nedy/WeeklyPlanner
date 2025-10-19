package com.example.weeklyplanner.persistence.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "task")
public class TaskEntity {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, index = true)
    private String name;

    @DatabaseField
    private String description;

    @DatabaseField(index = true)
    private String status;

    @DatabaseField(index = true)
    private String priority;

    @DatabaseField(index = true)
    private String category;

    @DatabaseField
    private String dueDate; // Should be date time object. Format: yyyy-MM-dd HH:mm:ss

    @DatabaseField
    private Integer durationMinutes;

    public TaskEntity(Integer id, String name, String description, String status, String priority, String category, String dueDate, Integer durationMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.dueDate = dueDate;
        this.durationMinutes = durationMinutes;
    }

    TaskEntity() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }
}