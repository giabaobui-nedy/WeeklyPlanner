package com.example.weeklyplanner.persistence.db;

import com.example.weeklyplanner.persistence.entity.PlanEntryEntity;
import com.example.weeklyplanner.persistence.entity.TaskEntity;
import com.example.weeklyplanner.persistence.entity.UserPreferenceEntity;
import com.example.weeklyplanner.persistence.entity.WeeklyPlanEntity;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


import java.sql.SQLException;

public class DatabaseInitializer
{
    public static void main(String[] args) {
        String database_url = "jdbc:sqlite:planner-database";
        // create a connection source to our database
        try (ConnectionSource connectionSource = new JdbcConnectionSource(database_url)) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, TaskEntity.class);
                TableUtils.createTableIfNotExists(connectionSource, UserPreferenceEntity.class);
                TableUtils.createTableIfNotExists(connectionSource, WeeklyPlanEntity.class);
                TableUtils.createTableIfNotExists(connectionSource, PlanEntryEntity.class);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
