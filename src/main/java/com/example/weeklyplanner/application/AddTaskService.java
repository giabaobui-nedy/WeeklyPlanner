package com.example.weeklyplanner.application;

import com.example.weeklyplanner.persistence.repository.TaskRepositoryImpl;
import java.sql.SQLException;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.domain.port.TaskRepository;

public class AddTaskService {
    private final TaskRepository taskRepository = new TaskRepositoryImpl();

    public Task addTask(Task task) {
        try {
            return taskRepository.add(task);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add task: " + e.getMessage());
        }
    }
}
