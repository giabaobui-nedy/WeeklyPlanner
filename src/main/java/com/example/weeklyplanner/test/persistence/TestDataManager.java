package com.example.weeklyplanner.test.persistence;

import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.persistence.entity.UserPreferenceEntity;
import com.example.weeklyplanner.persistence.repository.UserPreferenceRepositoryImpl;

import java.sql.SQLException;
import java.time.LocalTime;

/**
 * Shared test data manager that creates and manages a single UserPreference
 * instance that can be reused across all test classes.
 */
public class TestDataManager {
    
    private static UserPreference sharedUserPreference;
    private static UserPreferenceEntity sharedUserPreferenceEntity;
    private static boolean initialized = false;
    
    /**
     * Initialize the shared test data. This should be called once at the start of test execution.
     */
    public static void initialize() throws SQLException {
        if (initialized) {
            return;
        }
        
        // Create the shared user preference domain object
        sharedUserPreference = new UserPreference(
            LocalTime.of(9, 0),  // 9:00 AM
            LocalTime.of(17, 0)  // 5:00 PM
        );
        
        // Create the shared user preference entity
        sharedUserPreferenceEntity = new UserPreferenceEntity();
        sharedUserPreferenceEntity.setDayStart("09:00");
        sharedUserPreferenceEntity.setDayEnd("17:00");
        
        // Set required timestamp fields
        String currentTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        sharedUserPreferenceEntity.setCreatedAt(currentTime);
        sharedUserPreferenceEntity.setUpdatedAt(currentTime);
        
        // Save the entity to get an ID
        UserPreferenceRepositoryImpl userPrefRepo = new UserPreferenceRepositoryImpl();
        UserPreference savedEntity = userPrefRepo.add(sharedUserPreference);
        initialized = true;
    }
    
    /**
     * Get the shared UserPreference domain object.
     */
    public static UserPreference getSharedUserPreference() {
        if (!initialized) {
            throw new IllegalStateException("TestDataManager not initialized. Call initialize() first.");
        }
        return sharedUserPreference;
    }
    
    /**
     * Get the shared UserPreferenceEntity with ID.
     */
    public static UserPreferenceEntity getSharedUserPreferenceEntity() {
        if (!initialized) {
            throw new IllegalStateException("TestDataManager not initialized. Call initialize() first.");
        }
        return sharedUserPreferenceEntity;
    }
    
    /**
     * Reset the test data manager (useful for test cleanup).
     */
    public static void reset() {
        sharedUserPreference = null;
        sharedUserPreferenceEntity = null;
        initialized = false;
    }
}
