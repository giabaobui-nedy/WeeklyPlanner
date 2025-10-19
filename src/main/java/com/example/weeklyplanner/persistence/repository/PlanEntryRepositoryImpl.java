package com.example.weeklyplanner.persistence.repository;

import com.example.weeklyplanner.domain.model.PlanEntry;
import com.example.weeklyplanner.domain.port.PlanEntryRepository;
import com.example.weeklyplanner.persistence.db.DatabaseConfigurator;
import com.example.weeklyplanner.persistence.entity.PlanEntryEntity;
import com.example.weeklyplanner.persistence.mapper.PlanEntryMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.List;

public class PlanEntryRepositoryImpl implements PlanEntryRepository {
    private final Dao<PlanEntryEntity, Integer> planEntryDao;

    public PlanEntryRepositoryImpl() {
        try (JdbcConnectionSource jcs = DatabaseConfigurator.getDatabaseConnectionSource()) {
            planEntryDao = DaoManager.createDao(jcs, PlanEntryEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlanEntry> getAll() throws SQLException {
        QueryBuilder<PlanEntryEntity, Integer> queryBuilder = planEntryDao.queryBuilder();
        queryBuilder.orderBy("id", false);
        try {
            List<PlanEntryEntity> rows = queryBuilder.query();
            PlanEntryMapper mapper = new PlanEntryMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public PlanEntry getOne(Integer id) throws SQLException {
        try {
            PlanEntryEntity entity = planEntryDao.queryForId(id);
            return new PlanEntryMapper().convertEntityToDomain(entity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public PlanEntry add(PlanEntry entity) throws SQLException {
        try {
            PlanEntryEntity planEntryEntity = new PlanEntryMapper().convertDomainToEntity(entity);
            planEntryDao.create(planEntryEntity);
            return new PlanEntryMapper().convertEntityToDomain(planEntryEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public PlanEntry update(PlanEntry entity) throws SQLException {
        try {
            PlanEntryEntity planEntryEntity = new PlanEntryMapper().convertDomainToEntity(entity);
            planEntryDao.update(planEntryEntity);
            return new PlanEntryMapper().convertEntityToDomain(planEntryEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public PlanEntry delete(Integer id) throws SQLException {
        try {
            PlanEntryEntity entity = planEntryDao.queryForId(id);
            if (entity != null) {
                planEntryDao.delete(entity);
                return new PlanEntryMapper().convertEntityToDomain(entity);
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<PlanEntry> getEntriesByWeeklyPlanId(Integer weeklyPlanId) throws SQLException {
        QueryBuilder<PlanEntryEntity, Integer> queryBuilder = planEntryDao.queryBuilder();
        queryBuilder.where().eq("plan_id", weeklyPlanId);
        try {
            List<PlanEntryEntity> rows = queryBuilder.query();
            PlanEntryMapper mapper = new PlanEntryMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<PlanEntry> getEntriesByTaskId(Integer taskId) throws SQLException {
        QueryBuilder<PlanEntryEntity, Integer> queryBuilder = planEntryDao.queryBuilder();
        queryBuilder.where().eq("task_id", taskId);
        try {
            List<PlanEntryEntity> rows = queryBuilder.query();
            PlanEntryMapper mapper = new PlanEntryMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<PlanEntry> getEntriesByDayIndex(Integer dayIndex) throws SQLException {
        QueryBuilder<PlanEntryEntity, Integer> queryBuilder = planEntryDao.queryBuilder();
        queryBuilder.where().eq("dayIndex", dayIndex);
        try {
            List<PlanEntryEntity> rows = queryBuilder.query();
            PlanEntryMapper mapper = new PlanEntryMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }
}
