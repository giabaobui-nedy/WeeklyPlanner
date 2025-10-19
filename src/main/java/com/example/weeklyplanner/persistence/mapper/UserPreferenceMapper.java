package com.example.weeklyplanner.persistence.mapper;

import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.persistence.entity.UserPreferenceEntity;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UserPreferenceMapper implements BaseMapper<UserPreferenceEntity, UserPreference> {
    
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public UserPreference convertEntityToDomain(UserPreferenceEntity entity) {
        if (entity == null) return null;
        
        LocalTime dayStart = LocalTime.parse(entity.getDayStart(), TIME_FORMATTER);
        LocalTime dayEnd = LocalTime.parse(entity.getDayEnd(), TIME_FORMATTER);
        
        return new UserPreference(dayStart, dayEnd);
    }

    @Override
    public UserPreferenceEntity convertDomainToEntity(UserPreference domain) {
        if (domain == null) return null;
        
        UserPreferenceEntity entity = new UserPreferenceEntity();
        entity.setId(null); // Will be set by database
        entity.setDayStart(domain.getDayStart().format(TIME_FORMATTER));
        entity.setDayEnd(domain.getDayEnd().format(TIME_FORMATTER));
        entity.setCreatedAt(java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        entity.setUpdatedAt(java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return entity;
    }

    @Override
    public List<UserPreference> convertEntityListToDomainList(List<UserPreferenceEntity> entities) {
        List<UserPreference> domainList = new ArrayList<>();
        for (UserPreferenceEntity entity : entities) {
            domainList.add(convertEntityToDomain(entity));
        }
        return domainList;
    }

    @Override
    public List<UserPreferenceEntity> convertDomainListToEntityList(List<UserPreference> domains) {
        List<UserPreferenceEntity> entityList = new ArrayList<>();
        for (UserPreference domain : domains) {
            entityList.add(convertDomainToEntity(domain));
        }
        return entityList;
    }
}
