package com.example.weeklyplanner.test.persistence.db;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.example.weeklyplanner.domain.enumeration.TaskPriority;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.persistence.repository.TaskRepositoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class TestDatabase {
    private final TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();

    @Test
    public void testAddTask() throws SQLException {
        // Task task = new Task(null, "Test Task", "This is a test task", TaskStatus.TODO, TaskPriority.MEDIUM,
        //         "Test Category", LocalDate.now(), 30);
        // taskRepository.add(task);
    }

    @Test
    public void testGetTasksByStatus() throws SQLException {
        List<Task> tasks = taskRepository.getTasksByStatus(TaskStatus.TODO);
        Assert.assertFalse("Number of tasks found is " + tasks.size(), tasks.isEmpty());
    }
}
