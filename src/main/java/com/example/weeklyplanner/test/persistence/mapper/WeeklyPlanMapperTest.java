package com.example.weeklyplanner.test.persistence.mapper;

import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.model.ISOWeek;
import com.example.weeklyplanner.persistence.entity.WeeklyPlanEntity;
import com.example.weeklyplanner.persistence.entity.UserPreferenceEntity;
import com.example.weeklyplanner.persistence.mapper.WeeklyPlanMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeeklyPlanMapperTest {
    
    private WeeklyPlanMapper mapper;
    
    @Before
    public void setUp() {
        mapper = new WeeklyPlanMapper();
    }
    
    @Test
    public void testConvertEntityToDomain() {
        // Given
        WeeklyPlanEntity entity = new WeeklyPlanEntity();
        entity.setId(1);
        entity.setYear(2024);
        entity.setWeek(15);
        entity.setVersion("1.0");
        
        UserPreferenceEntity userPref = new UserPreferenceEntity();
        userPref.setId(100);
        entity.setUserPreference(userPref);
        
        // When
        WeeklyPlan domain = mapper.convertEntityToDomain(entity);
        
        // Then
        Assert.assertNotNull(domain);
        Assert.assertNotNull(domain.getIsoWeek());
        Assert.assertEquals(2024, domain.getIsoWeek().getYear());
        Assert.assertEquals(15, domain.getIsoWeek().getWeek());
        Assert.assertNotNull(domain.getDayPlans());
        Assert.assertTrue(domain.getDayPlans().isEmpty()); // DayPlans would be loaded separately
    }
    
    @Test
    public void testConvertDomainToEntity() {
        // Given
        ISOWeek isoWeek = new ISOWeek(2024, 20);
        WeeklyPlan domain = new WeeklyPlan(isoWeek);
        
        // When
        WeeklyPlanEntity entity = mapper.convertDomainToEntity(domain);
        
        // Then
        Assert.assertNotNull(entity);
        Assert.assertNull(entity.getId()); // Will be set by database
        Assert.assertEquals(2024, entity.getYear().intValue());
        Assert.assertEquals(20, entity.getWeek().intValue());
        Assert.assertEquals("1.0", entity.getVersion());
        Assert.assertNotNull(entity.getCreatedAt());
        Assert.assertNotNull(entity.getUpdatedAt());
        Assert.assertNull(entity.getUserPreference()); // Would be set separately
    }
    
    @Test
    public void testConvertEntityToDomainWithNullValues() {
        // Given
        WeeklyPlanEntity entity = new WeeklyPlanEntity();
        entity.setId(1);
        entity.setYear(2024);
        entity.setWeek(10);
        entity.setVersion("2.0");
        // userPreference is null
        
        // When
        WeeklyPlan domain = mapper.convertEntityToDomain(entity);
        
        // Then
        Assert.assertNotNull(domain);
        Assert.assertEquals(2024, domain.getIsoWeek().getYear());
        Assert.assertEquals(10, domain.getIsoWeek().getWeek());
    }
    
    @Test
    public void testConvertNullEntity() {
        // When
        WeeklyPlan domain = mapper.convertEntityToDomain(null);
        
        // Then
        Assert.assertNull(domain);
    }
    
    @Test
    public void testConvertNullDomain() {
        // When
        WeeklyPlanEntity entity = mapper.convertDomainToEntity(null);
        
        // Then
        Assert.assertNull(entity);
    }
}