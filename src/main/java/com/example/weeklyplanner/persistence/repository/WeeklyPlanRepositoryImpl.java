package com.example.weeklyplanner.persistence.repository;

import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.port.WeeklyPlanRepository;
import com.example.weeklyplanner.persistence.db.DatabaseConfigurator;
import com.example.weeklyplanner.persistence.entity.WeeklyPlanEntity;
import com.example.weeklyplanner.persistence.mapper.WeeklyPlanMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.List;

public class WeeklyPlanRepositoryImpl implements WeeklyPlanRepository {
    private final Dao<WeeklyPlanEntity, Integer> weeklyPlanDao;

    public WeeklyPlanRepositoryImpl() {
        try (JdbcConnectionSource jcs = DatabaseConfigurator.getDatabaseConnectionSource()) {
            weeklyPlanDao = DaoManager.createDao(jcs, WeeklyPlanEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WeeklyPlan> getAll() throws SQLException {
        QueryBuilder<WeeklyPlanEntity, Integer> queryBuilder = weeklyPlanDao.queryBuilder();
        queryBuilder.orderBy("id", false);
        try {
            List<WeeklyPlanEntity> rows = queryBuilder.query();
            WeeklyPlanMapper mapper = new WeeklyPlanMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public WeeklyPlan getOne(Integer id) throws SQLException {
        try {
            WeeklyPlanEntity entity = weeklyPlanDao.queryForId(id);
            return new WeeklyPlanMapper().convertEntityToDomain(entity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public WeeklyPlan add(WeeklyPlan entity) throws SQLException {
        try {
            WeeklyPlanEntity weeklyPlanEntity = new WeeklyPlanMapper().convertDomainToEntity(entity);
            weeklyPlanDao.create(weeklyPlanEntity);
            return new WeeklyPlanMapper().convertEntityToDomain(weeklyPlanEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public WeeklyPlan update(WeeklyPlan entity) throws SQLException {
        try {
            WeeklyPlanEntity weeklyPlanEntity = new WeeklyPlanMapper().convertDomainToEntity(entity);
            weeklyPlanDao.update(weeklyPlanEntity);
            return new WeeklyPlanMapper().convertEntityToDomain(weeklyPlanEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public WeeklyPlan delete(Integer id) throws SQLException {
        try {
            WeeklyPlanEntity entity = weeklyPlanDao.queryForId(id);
            if (entity != null) {
                weeklyPlanDao.delete(entity);
                return new WeeklyPlanMapper().convertEntityToDomain(entity);
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<WeeklyPlan> getPlansByYearAndWeek(Integer year, Integer week) throws SQLException {
        QueryBuilder<WeeklyPlanEntity, Integer> queryBuilder = weeklyPlanDao.queryBuilder();
        queryBuilder.where().eq("year", year).and().eq("week", week);
        try {
            List<WeeklyPlanEntity> rows = queryBuilder.query();
            WeeklyPlanMapper mapper = new WeeklyPlanMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<WeeklyPlan> getPlansByVersion(String version) throws SQLException {
        QueryBuilder<WeeklyPlanEntity, Integer> queryBuilder = weeklyPlanDao.queryBuilder();
        queryBuilder.where().eq("version", version);
        try {
            List<WeeklyPlanEntity> rows = queryBuilder.query();
            WeeklyPlanMapper mapper = new WeeklyPlanMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }
}
