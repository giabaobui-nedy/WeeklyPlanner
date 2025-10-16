package com.example.weeklyplanner.persistence.mapper;

import com.example.weeklyplanner.domain.enumeration.TaskPriority;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.persistence.entity.TaskEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                taskEntity.getId(),
                taskEntity.getName(),
                taskEntity.getDescription(),
                TaskStatus.valueOf(taskEntity.getStatus()),
                TaskPriority.valueOf(taskEntity.getPriority()),
                taskEntity.getCategory(),
                LocalDate.parse(taskEntity.getDueDate()),
                taskEntity.getDurationMinutes());
    }

    @Override
    public TaskEntity convertDomainToEntity(Task domain) {
        return new TaskEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getStatus().name(),
                domain.getPriority().name(),
                domain.getCategory(),
                domain.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                domain.getDurationMinutes());
    }

    @Override
    public List<TaskEntity> convertDomainListToEntityList(List<Task> domains) {
        List<TaskEntity> taskEntityList = new ArrayList<>();
        for (Task domain : domains) {
            taskEntityList.add(convertDomainToEntity(domain));
        }
        return taskEntityList;
    }
}
