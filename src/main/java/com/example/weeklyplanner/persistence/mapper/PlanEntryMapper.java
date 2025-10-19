package com.example.weeklyplanner.persistence.mapper;

import com.example.weeklyplanner.domain.model.PlanEntry;
import com.example.weeklyplanner.persistence.entity.PlanEntryEntity;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;

public class PlanEntryMapper implements BaseMapper<PlanEntryEntity, PlanEntry> {

    @Override
    public PlanEntry convertEntityToDomain(PlanEntryEntity entity) {
        if (entity == null) return null;
        
        // Convert time components to LocalTime
        LocalTime startTime = LocalTime.of(entity.getStartHour(), entity.getStartMinute());
        LocalTime endTime = LocalTime.of(entity.getEndHour(), entity.getEndMinute());
        
        return new PlanEntry(
            entity.getId(),
            entity.getTask() != null ? entity.getTask().getId() : null,
            entity.getPlan() != null ? entity.getPlan().getId() : null,
            entity.getDayIndex(),
            startTime,
            endTime
        );
    }

    @Override
    public PlanEntryEntity convertDomainToEntity(PlanEntry domain) {
        if (domain == null) return null;
        
        PlanEntryEntity entity = new PlanEntryEntity();
        entity.setId(domain.getId());
        entity.setDayIndex(domain.getDayIndex());
        entity.setDurationMinutes(domain.getDurationMinutes());
        
        // Extract time components from LocalTime
        entity.setStartHour(domain.getStartTime().getHour());
        entity.setStartMinute(domain.getStartTime().getMinute());
        entity.setEndHour(domain.getEndTime().getHour());
        entity.setEndMinute(domain.getEndTime().getMinute());
        
        // Note: Task and Plan foreign keys would need to be set separately
        // This requires the actual entity objects, not just IDs
        
        return entity;
    }

    @Override
    public List<PlanEntry> convertEntityListToDomainList(List<PlanEntryEntity> entities) {
        List<PlanEntry> domainList = new ArrayList<>();
        for (PlanEntryEntity entity : entities) {
            domainList.add(convertEntityToDomain(entity));
        }
        return domainList;
    }

    @Override
    public List<PlanEntryEntity> convertDomainListToEntityList(List<PlanEntry> domains) {
        List<PlanEntryEntity> entityList = new ArrayList<>();
        for (PlanEntry domain : domains) {
            entityList.add(convertDomainToEntity(domain));
        }
        return entityList;
    }
    
}
