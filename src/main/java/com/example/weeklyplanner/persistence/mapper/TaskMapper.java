package com.example.weeklyplanner.persistence.mapper;

import com.example.weeklyplanner.domain.enumeration.TaskPriority;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.persistence.entity.TaskEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskMapper implements BaseMapper<TaskEntity, Task> {

    @Override
    public List<Task> convertEntityListToDomainList(List<TaskEntity> taskEntityList) {
        List<Task> taskList = new ArrayList<>();
        for (TaskEntity te : taskEntityList) {
            taskList.add(convertEntityToDomain(te));
        }
        return taskList;
    }

    @Override
    public Task convertEntityToDomain(TaskEntity taskEntity) {
        return new Task(
                taskEntity.id,
                taskEntity.name,
                taskEntity.description,
                TaskStatus.valueOf(taskEntity.status),
                TaskPriority.valueOf(taskEntity.priority),
                taskEntity.category,
                LocalDate.parse(taskEntity.dueDate),
                taskEntity.durationMinutes);
    }
}
