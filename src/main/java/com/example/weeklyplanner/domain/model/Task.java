// domain/model/Task.java
package com.example.weeklyplanner.domain.model;

import com.example.weeklyplanner.domain.enumeration.TaskPriority;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;

import java.time.LocalDate;

public class Task {
    private final Integer id; // null for new entities
    private final String name;
    private final String description;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final String category;
    private final LocalDate dueDate;
    private final Integer durationMinutes;

    public Task(Integer id, String name, String description, TaskStatus status,
            TaskPriority priority, String category, LocalDate dueDate, Integer durationMinutes) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name required");
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status == null ? TaskStatus.TODO : status;
        this.priority = priority == null ? TaskPriority.MEDIUM : priority;
        this.category = category;
        this.dueDate = dueDate;
        this.durationMinutes = durationMinutes;
    }

}
