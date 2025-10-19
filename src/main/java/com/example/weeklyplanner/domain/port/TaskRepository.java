package com.example.weeklyplanner.domain.port;

import java.sql.SQLException;
import java.util.List;

import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Task;

public interface TaskRepository extends BaseRepository<Task> {
    public List<Task> getTasksByStatus(TaskStatus status) throws SQLException;
}
