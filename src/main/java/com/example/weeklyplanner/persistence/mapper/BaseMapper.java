package com.example.weeklyplanner.domain.mapper;

import java.util.List;

public interface BaseMapper<EC, DC> {
    public List<DC> convertEntityListToDomainList(List<EC> entities);

    public DC convertEntityToDomain(EC entity);
}
