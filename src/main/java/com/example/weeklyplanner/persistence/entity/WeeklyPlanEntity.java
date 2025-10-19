package com.example.weeklyplanner.persistence.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "weekly_plan")
public class WeeklyPlanEntity {
    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, index = true)
    private Integer year;

    @DatabaseField(canBeNull = false, index = true)
    private Integer week;

    @DatabaseField(canBeNull = false, index = true)
    private String version;

    @DatabaseField(canBeNull = false, index = true)
    private String requestId;

    @DatabaseField(canBeNull = false, index = true)
    private String userPreference;

    @DatabaseField(canBeNull = false, index = true)
    private String createdAt;

    @DatabaseField(canBeNull = false, index = true)
    private String updatedAt;
}
