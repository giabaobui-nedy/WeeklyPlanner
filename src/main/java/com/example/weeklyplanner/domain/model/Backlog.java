package com.example.weeklyplanner.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Backlog {
    private final List<Task> tasks;

    public Backlog() {
        this.tasks = new ArrayList<Task>();
    }

    public Backlog(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
