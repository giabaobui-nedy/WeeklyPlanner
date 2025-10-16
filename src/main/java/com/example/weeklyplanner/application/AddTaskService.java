package com.example.weeklyplanner.application;

import com.example.weeklyplanner.domain.repository.TaskRepository;
import com.example.weeklyplanner.persistence.repository.TaskRepositoryImpl;
import java.sql.SQLException;
import com.example.weeklyplanner.domain.model.Task;

public class AddTaskService {
    private final TaskRepository taskRepository = new TaskRepositoryImpl();

    public void addTask(Task task) {
        try {
            taskRepository.add(task);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
