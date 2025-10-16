package com.example.weeklyplanner.persistence.mapper;

import java.util.List;

public interface BaseMapper<EC, DC> {
    public List<DC> convertEntityListToDomainList(List<EC> entities);

    public DC convertEntityToDomain(EC entity);

    public EC convertDomainToEntity(DC domain);

    public List<EC> convertDomainListToEntityList(List<DC> domains);
}
