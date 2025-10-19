package com.example.weeklyplanner.test.persistence.mapper;

import com.example.weeklyplanner.domain.model.PlanEntry;
import com.example.weeklyplanner.persistence.entity.PlanEntryEntity;
import com.example.weeklyplanner.persistence.entity.TaskEntity;
import com.example.weeklyplanner.persistence.entity.WeeklyPlanEntity;
import com.example.weeklyplanner.persistence.mapper.PlanEntryMapper;

import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlanEntryMapperTest {
    
    private PlanEntryMapper mapper;
    
    @Before
    public void setUp() {
        mapper = new PlanEntryMapper();
    }
    
    @Test
    public void testConvertEntityToDomain() {
        // Given
        PlanEntryEntity entity = new PlanEntryEntity();
        entity.setId(1);
        entity.setDayIndex(1); // Monday
        entity.setStartHour(9);
        entity.setStartMinute(0);
        entity.setEndHour(10);
        entity.setEndMinute(30);
        entity.setDurationMinutes(90);
        
        TaskEntity task = new TaskEntity();
        task.setId(100);
        entity.setTask(task);
        
        WeeklyPlanEntity plan = new WeeklyPlanEntity();
        plan.setId(200);
        entity.setPlan(plan);
        
        // When
        PlanEntry domain = mapper.convertEntityToDomain(entity);
        
        // Then
        Assert.assertNotNull(domain);
        Assert.assertEquals(1, domain.getId().intValue());
        Assert.assertEquals(100, domain.getTaskId().intValue());
        Assert.assertEquals(200, domain.getDayPlanId().intValue());
        Assert.assertEquals(1, domain.getDayIndex().intValue());
        Assert.assertEquals(LocalTime.of(9, 0), domain.getStartTime());
        Assert.assertEquals(LocalTime.of(10, 30), domain.getEndTime());
        Assert.assertEquals(90, domain.getDurationMinutes().intValue());
    }
    
    @Test
    public void testConvertDomainToEntity() {
        // Given: has no task and plan entity yet!
        PlanEntry domain = new PlanEntry(
            1, 100, 200, 1, // id, taskId, dayPlanId, dayIndex
            LocalTime.of(14, 15), // 2:15 PM
            LocalTime.of(15, 45)  // 3:45 PM
        );
        
        // When
        PlanEntryEntity entity = mapper.convertDomainToEntity(domain);
        
        // Then
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getId().intValue());
        Assert.assertEquals(0, entity.getTask() != null ? entity.getTask().getId() : 0);
        Assert.assertEquals(0, entity.getPlan() != null ? entity.getPlan().getId() : 0);
        Assert.assertEquals(1, entity.getDayIndex().intValue());
        Assert.assertEquals(14, entity.getStartHour().intValue());
        Assert.assertEquals(15, entity.getStartMinute().intValue());
        Assert.assertEquals(15, entity.getEndHour().intValue());
        Assert.assertEquals(45, entity.getEndMinute().intValue());
        Assert.assertEquals(90, entity.getDurationMinutes().intValue()); // 1.5 hours = 90 minutes
    }
    
    @Test
    public void testConvertEntityToDomainWithNullValues() {
        // Given
        PlanEntryEntity entity = new PlanEntryEntity();
        entity.setId(1);
        entity.setDayIndex(1);
        entity.setStartHour(9);
        entity.setStartMinute(0);
        entity.setEndHour(10);
        entity.setEndMinute(0);
        entity.setDurationMinutes(60);
        // task and plan are null
        
        // When
        PlanEntry domain = mapper.convertEntityToDomain(entity);
        
        // Then
        Assert.assertNotNull(domain);
        Assert.assertEquals(1, domain.getId().intValue());
        Assert.assertNull(domain.getTaskId());
        Assert.assertNull(domain.getDayPlanId());
        Assert.assertEquals(1, domain.getDayIndex().intValue());
        Assert.assertEquals(LocalTime.of(9, 0), domain.getStartTime());
        Assert.assertEquals(LocalTime.of(10, 0), domain.getEndTime());
        Assert.assertEquals(60, domain.getDurationMinutes().intValue());
    }
    
    @Test
    public void testConvertDomainToEntityWithNullValues() {
        // Given
        PlanEntry domain = new PlanEntry(
            null, null, null, 1, // null IDs
            LocalTime.of(8, 0),
            LocalTime.of(9, 0)
        );
        
        // When
        PlanEntryEntity entity = mapper.convertDomainToEntity(domain);
        
        // Then
        Assert.assertNotNull(entity);
        Assert.assertNull(entity.getId());
        Assert.assertNull(entity.getTask());
        Assert.assertNull(entity.getPlan());
        Assert.assertEquals(1, entity.getDayIndex().intValue());
        Assert.assertEquals(8, entity.getStartHour().intValue());
        Assert.assertEquals(0, entity.getStartMinute().intValue());
        Assert.assertEquals(9, entity.getEndHour().intValue());
        Assert.assertEquals(0, entity.getEndMinute().intValue());
        Assert.assertEquals(60, entity.getDurationMinutes().intValue());
    }
    
    @Test
    public void testConvertNullEntity() {
        // When
        PlanEntry domain = mapper.convertEntityToDomain(null);
        
        // Then
        Assert.assertNull(domain);
    }
    
    @Test
    public void testConvertNullDomain() {
        // When
        PlanEntryEntity entity = mapper.convertDomainToEntity(null);
        
        // Then
        Assert.assertNull(entity);
    }
}
