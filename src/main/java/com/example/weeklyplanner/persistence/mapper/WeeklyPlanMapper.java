package com.example.weeklyplanner.persistence.mapper;

import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.model.ISOWeek;
import com.example.weeklyplanner.persistence.entity.WeeklyPlanEntity;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeeklyPlanMapper implements BaseMapper<WeeklyPlanEntity, WeeklyPlan> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public WeeklyPlan convertEntityToDomain(WeeklyPlanEntity entity) {
        if (entity == null)
            return null;

        ISOWeek isoWeek = new ISOWeek(entity.getYear(), entity.getWeek());
        WeeklyPlan weeklyPlan = new WeeklyPlan(isoWeek);

        // Note: DayPlans would need to be loaded separately via PlanEntryRepository
        // This is a simplified conversion for now

        return weeklyPlan;
    }

    @Override
    public WeeklyPlanEntity convertDomainToEntity(WeeklyPlan domain) {
        if (domain == null)
            return null;

        WeeklyPlanEntity entity = new WeeklyPlanEntity();
        entity.setId(domain.getIsoWeek() != null ? null : null); // Will be set by database
        entity.setYear(domain.getIsoWeek().getYear());
        entity.setWeek(domain.getIsoWeek().getWeek());
        entity.setVersion("1.0"); // Default version
        entity.setCreatedAt(LocalDateTime.now().format(DATE_FORMATTER));
        entity.setUpdatedAt(LocalDateTime.now().format(DATE_FORMATTER));

        // Note: UserPreference would need to be set separately
        // PlanEntries would be handled separately via PlanEntryRepository

        return entity;
    }

    @Override
    public List<WeeklyPlan> convertEntityListToDomainList(List<WeeklyPlanEntity> entities) {
        List<WeeklyPlan> domainList = new ArrayList<>();
        for (WeeklyPlanEntity entity : entities) {
            domainList.add(convertEntityToDomain(entity));
        }
        return domainList;
    }

    @Override
    public List<WeeklyPlanEntity> convertDomainListToEntityList(List<WeeklyPlan> domains) {
        List<WeeklyPlanEntity> entityList = new ArrayList<>();
        for (WeeklyPlan domain : domains) {
            entityList.add(convertDomainToEntity(domain));
        }
        return entityList;
    }
}
