package com.example.weeklyplanner.test.persistence.repository;

import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.persistence.repository.UserPreferenceRepositoryImpl;
import com.example.weeklyplanner.persistence.db.DatabaseInitializer;
import com.example.weeklyplanner.test.persistence.TestDataManager;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserPreferenceRepositoryTest {
    
    private UserPreferenceRepositoryImpl repository;
    
    @Before
    public void setUp() throws SQLException {
        // Initialize database
        DatabaseInitializer.main(new String[]{});
        
        // Initialize shared test data
        TestDataManager.initialize();
        
        // Create repository
        repository = new UserPreferenceRepositoryImpl();
    }
    
    @Test
    public void testAddUserPreference() throws SQLException {
        // Given - use the shared user preference
        UserPreference userPreference = TestDataManager.getSharedUserPreference();
        
        // When
        UserPreference savedPreference = repository.add(userPreference);
        
        // Then
        Assert.assertNotNull(savedPreference);
        Assert.assertEquals(userPreference.getDayStart(), savedPreference.getDayStart());
        Assert.assertEquals(userPreference.getDayEnd(), savedPreference.getDayEnd());
    }
    
    @Test
    public void testGetAllUserPreferences() throws SQLException {
        // Given - the shared user preference should already be in the database
        // from TestDataManager.initialize()
        
        // When
        List<UserPreference> preferences = repository.getAll();
        
        // Then
        Assert.assertNotNull(preferences);
        Assert.assertTrue(preferences.size() >= 1);
        
        // Verify the shared preference is in the results
        UserPreference sharedPref = TestDataManager.getSharedUserPreference();
        boolean found = false;
        for (UserPreference pref : preferences) {
            if (pref.getDayStart().equals(sharedPref.getDayStart()) && 
                pref.getDayEnd().equals(sharedPref.getDayEnd())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Shared user preference should be found in results", found);
    }
}