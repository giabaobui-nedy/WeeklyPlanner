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

    @DatabaseField(canBeNull = false, index = true, foreign = true, foreignAutoRefresh = true)
    private UserPreferenceEntity userPreference;

    @DatabaseField(canBeNull = false, index = true)
    private String createdAt;

    @DatabaseField(canBeNull = false, index = true)
    private String updatedAt;

    // Constructors
    public WeeklyPlanEntity() {
        // ORMLite requires a no-arg constructor
    }

    public WeeklyPlanEntity(Integer year, Integer week, String version,
            UserPreferenceEntity userPreference, String createdAt, String updatedAt) {
        this.year = year;
        this.week = week;
        this.version = version;
        this.userPreference = userPreference;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public UserPreferenceEntity getUserPreference() {
        return userPreference;
    }

    public void setUserPreference(UserPreferenceEntity userPreference) {
        this.userPreference = userPreference;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
