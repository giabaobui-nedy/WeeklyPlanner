package com.example.weeklyplanner.persistence.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_preference")
public class UserPreferenceEntity {
    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, index = true)
    private String dayStart; // Format: HH:mm

    @DatabaseField(canBeNull = false, index = true)
    private String dayEnd; // Format: HH:mm

    @DatabaseField(canBeNull = false, index = true)
    private String createdAt;

    @DatabaseField(canBeNull = false, index = true)
    private String updatedAt;

    // Constructors
    public UserPreferenceEntity() {
        // ORMLite requires a no-arg constructor
    }

    public UserPreferenceEntity(String dayStart, String dayEnd, 
                               String createdAt, String updatedAt) {
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
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

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
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
