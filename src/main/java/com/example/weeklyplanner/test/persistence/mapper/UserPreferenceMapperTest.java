package com.example.weeklyplanner.test.persistence.mapper;

import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.persistence.entity.UserPreferenceEntity;
import com.example.weeklyplanner.persistence.mapper.UserPreferenceMapper;

import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserPreferenceMapperTest {
    
    private UserPreferenceMapper mapper;
    
    @Before
    public void setUp() {
        mapper = new UserPreferenceMapper();
    }
    
    @Test
    public void testConvertEntityToDomain() {
        // Given
        UserPreferenceEntity entity = new UserPreferenceEntity();
        entity.setId(1);
        entity.setDayStart("09:00");
        entity.setDayEnd("17:00");
        entity.setCreatedAt("2024-01-01 10:00:00");
        entity.setUpdatedAt("2024-01-01 10:00:00");
        
        // When
        UserPreference domain = mapper.convertEntityToDomain(entity);
        
        // Then
        Assert.assertNotNull(domain);
        Assert.assertEquals(LocalTime.of(9, 0), domain.getDayStart());
        Assert.assertEquals(LocalTime.of(17, 0), domain.getDayEnd());
    }
    
    @Test
    public void testConvertDomainToEntity() {
        // Given
        UserPreference domain = new UserPreference(
            LocalTime.of(8, 30), // 8:30 AM
            LocalTime.of(18, 0)  // 6:00 PM
        );
        
        // When
        UserPreferenceEntity entity = mapper.convertDomainToEntity(domain);
        
        // Then
        Assert.assertNotNull(entity);
        Assert.assertNull(entity.getId()); // Will be set by database
        Assert.assertEquals("08:30", entity.getDayStart());
        Assert.assertEquals("18:00", entity.getDayEnd());
        Assert.assertNotNull(entity.getCreatedAt());
        Assert.assertNotNull(entity.getUpdatedAt());
    }
    
    @Test
    public void testConvertEntityToDomainWithDifferentTimes() {
        // Given
        UserPreferenceEntity entity = new UserPreferenceEntity();
        entity.setId(2);
        entity.setDayStart("07:15");
        entity.setDayEnd("19:45");
        
        // When
        UserPreference domain = mapper.convertEntityToDomain(entity);
        
        // Then
        Assert.assertNotNull(domain);
        Assert.assertEquals(LocalTime.of(7, 15), domain.getDayStart());
        Assert.assertEquals(LocalTime.of(19, 45), domain.getDayEnd());
    }
    
    @Test
    public void testConvertDomainToEntityWithDifferentTimes() {
        // Given
        UserPreference domain = new UserPreference(
            LocalTime.of(6, 0),  // 6:00 AM
            LocalTime.of(22, 30) // 10:30 PM
        );
        
        // When
        UserPreferenceEntity entity = mapper.convertDomainToEntity(domain);
        
        // Then
        Assert.assertNotNull(entity);
        Assert.assertEquals("06:00", entity.getDayStart());
        Assert.assertEquals("22:30", entity.getDayEnd());
    }
    
    @Test
    public void testConvertNullEntity() {
        // When
        UserPreference domain = mapper.convertEntityToDomain(null);
        
        // Then
        Assert.assertNull(domain);
    }
    
    @Test
    public void testConvertNullDomain() {
        // When
        UserPreferenceEntity entity = mapper.convertDomainToEntity(null);
        
        // Then
        Assert.assertNull(entity);
    }
}