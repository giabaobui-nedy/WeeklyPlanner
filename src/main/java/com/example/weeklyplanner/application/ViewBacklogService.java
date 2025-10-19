package com.example.weeklyplanner.application;

import java.sql.SQLException;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Backlog;
import com.example.weeklyplanner.domain.port.TaskRepository;
import com.example.weeklyplanner.persistence.repository.TaskRepositoryImpl;

public class ViewBacklogService {
    private Backlog backlog;
    private final TaskRepository taskRepository = new TaskRepositoryImpl();

    public ViewBacklogService() {
        refreshBacklog();
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void refreshBacklog() {
        try {
            backlog = new Backlog(taskRepository.getTasksByStatus(TaskStatus.TODO));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to refresh backlog: " + e.getMessage());
        }
    }
}
