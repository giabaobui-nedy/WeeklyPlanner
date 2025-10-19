package com.example.weeklyplanner.application;

import java.sql.SQLException;
import java.util.List;


import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.domain.port.TaskRepository;
import com.example.weeklyplanner.persistence.repository.TaskRepositoryImpl;

public class ViewBacklogService {
    private final TaskRepository taskRepository = new TaskRepositoryImpl();

    public List<Task> getBacklog() throws SQLException {
        return taskRepository.getTasksByStatus(TaskStatus.TODO);
    }
}

