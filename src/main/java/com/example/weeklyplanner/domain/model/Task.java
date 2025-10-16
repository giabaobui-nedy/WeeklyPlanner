// domain/model/Task.java
package com.example.weeklyplanner.domain.model;

import java.time.LocalDate;

public class Task {
    public enum Status {TODO, IN_PROGRESS, DONE}

    public enum Priority {LOW, MEDIUM, HIGH}

    private final Integer id;                 // null for new entities
    private final String name;
    private final String description;
    private final Status status;
    private final Priority priority;
    private final String category;
    private final LocalDate dueDate;
    private final Integer durationMinutes;

    public Task(Integer id, String name, String description, Status status,
                Priority priority, String category, LocalDate dueDate, Integer durationMinutes) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status == null ? Status.TODO : status;
        this.priority = priority == null ? Priority.MEDIUM : priority;
        this.category = category;
        this.dueDate = dueDate;
        this.durationMinutes = durationMinutes;
    }

    public Integer id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Status status() {
        return status;
    }

    public Priority priority() {
        return priority;
    }

    public String category() {
        return category;
    }

    public LocalDate dueDate() {
        return dueDate;
    }

    public Integer durationMinutes() {
        return durationMinutes;
    }

    public Task withId(Integer newId) {
        return new Task(newId, name, description, status, priority, category, dueDate, durationMinutes);
    }

    public Task markDone() {
        return new Task(id, name, description, Status.DONE, priority, category, dueDate, durationMinutes);
    }
}
