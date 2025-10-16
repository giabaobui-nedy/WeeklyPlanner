package com.example.weeklyplanner.persistence.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "task")
public class TaskEntity {

    @DatabaseField(generatedId = true)
    public Integer id;

    @DatabaseField(canBeNull = false, index = true)
    public String name;

    @DatabaseField
    public String description;

    @DatabaseField(dataType = DataType.ENUM_STRING, index = true)
    public String status;

    @DatabaseField(dataType = DataType.ENUM_STRING, index = true)
    public String priority;

    @DatabaseField(index = true)
    public String category;

    @DatabaseField
    public String dueDate; // ISO yyyy-MM-dd (keep TEXT for SQLite friendliness)

    @DatabaseField
    public Integer durationMinutes;

    public TaskEntity() {}
}