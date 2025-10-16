package com.example.weeklyplanner.domain.mapper;


import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.persistence.entity.TaskEntity;

import java.util.List;

public class TaskMapper implements BaseMapper<TaskEntity, Task> {

    @Override
    public List<Task> convertEntityListToDomainList(List<TaskEntity> entities) {
//        for (TaskEntity te : entities) {
//            te.status = null;
//            Task t = new Task(te.id, te.name, te.description, te.status, te.priority, te.category, te.dueDate, te.durationMinutes);
//        }
        return null;
    }

    @Override
    public Task convertEntityToDomain(TaskEntity entity) {
        return null;
    }
}
