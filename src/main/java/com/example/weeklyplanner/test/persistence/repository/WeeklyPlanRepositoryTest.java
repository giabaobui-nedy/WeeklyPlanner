package com.example.weeklyplanner.test.persistence.repository;

import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.model.DayPlan;
import com.example.weeklyplanner.domain.model.ISOWeek;
import com.example.weeklyplanner.persistence.repository.WeeklyPlanRepositoryImpl;
import com.example.weeklyplanner.persistence.entity.UserPreferenceEntity;
import com.example.weeklyplanner.persistence.db.DatabaseInitializer;
import com.example.weeklyplanner.test.persistence.TestDataManager;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeeklyPlanRepositoryTest {
    
    private WeeklyPlanRepositoryImpl repository;
    private UserPreferenceEntity sharedUserPref;
    
    @Before
    public void setUp() throws SQLException {
        
        // Initialize shared test data
        TestDataManager.initialize();
        
        // Create repository
        repository = new WeeklyPlanRepositoryImpl();
        
        // Get the shared user preference entity
        sharedUserPref = TestDataManager.getSharedUserPreferenceEntity();
    }
    
    @Test
    public void testCreateWeeklyPlan() throws SQLException {
        // When
        WeeklyPlan createdPlan = repository.createWeeklyPlan(2024, 15);
        
        // Then
        Assert.assertNotNull(createdPlan);
        Assert.assertNotNull(createdPlan.getIsoWeek());
        Assert.assertEquals(2024, createdPlan.getIsoWeek().getYear());
        Assert.assertEquals(15, createdPlan.getIsoWeek().getWeek());
        Assert.assertNotNull(createdPlan.getDayPlans());
        Assert.assertEquals(7, createdPlan.getDayPlans().size()); // 7 days of the week
    }
    @Test
    public void testGenerateWeeklyPlan() throws SQLException {
        // Given - first create a plan with some data
        repository.createWeeklyPlan(2024, 20);
        
        // When - generate a complete plan with all relationships
        WeeklyPlan generatedPlan = repository.generateWeeklyPlan(2024, 20);
        
        // Then
        Assert.assertNotNull(generatedPlan);
        Assert.assertNotNull(generatedPlan.getIsoWeek());
        Assert.assertEquals(2024, generatedPlan.getIsoWeek().getYear());
        Assert.assertEquals(20, generatedPlan.getIsoWeek().getWeek());
        Assert.assertNotNull(generatedPlan.getDayPlans());
        Assert.assertEquals(7, generatedPlan.getDayPlans().size());
        
        // Verify each day has a day plan
        for (int i = 0; i < 7; i++) {
            DayPlan dayPlan = generatedPlan.getDayPlans().get(i);
            Assert.assertNotNull(dayPlan);
            Assert.assertNotNull(dayPlan.getDay());
            Assert.assertNotNull(dayPlan.getTaskEntries());
        }
    }
}