package com.example.weeklyplanner.persistence.repository;

import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.domain.port.UserPreferenceRepository;
import com.example.weeklyplanner.persistence.db.DatabaseConfigurator;
import com.example.weeklyplanner.persistence.entity.UserPreferenceEntity;
import com.example.weeklyplanner.persistence.mapper.UserPreferenceMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.List;

public class UserPreferenceRepositoryImpl implements UserPreferenceRepository {
    private final Dao<UserPreferenceEntity, Integer> userPreferenceDao;

    public UserPreferenceRepositoryImpl() {
        try (JdbcConnectionSource jcs = DatabaseConfigurator.getDatabaseConnectionSource()) {
            userPreferenceDao = DaoManager.createDao(jcs, UserPreferenceEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserPreference> getAll() throws SQLException {
        QueryBuilder<UserPreferenceEntity, Integer> queryBuilder = userPreferenceDao.queryBuilder();
        queryBuilder.orderBy("id", false);
        try {
            List<UserPreferenceEntity> rows = queryBuilder.query();
            UserPreferenceMapper mapper = new UserPreferenceMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public UserPreference getOne(Integer id) throws SQLException {
        try {
            UserPreferenceEntity entity = userPreferenceDao.queryForId(id);
            return new UserPreferenceMapper().convertEntityToDomain(entity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public UserPreference add(UserPreference entity) throws SQLException {
        try {
            UserPreferenceEntity userPreferenceEntity = new UserPreferenceMapper().convertDomainToEntity(entity);
            userPreferenceDao.create(userPreferenceEntity);
            return new UserPreferenceMapper().convertEntityToDomain(userPreferenceEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public UserPreference update(UserPreference entity) throws SQLException {
        try {
            UserPreferenceEntity userPreferenceEntity = new UserPreferenceMapper().convertDomainToEntity(entity);
            userPreferenceDao.update(userPreferenceEntity);
            return new UserPreferenceMapper().convertEntityToDomain(userPreferenceEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public UserPreference delete(Integer id) throws SQLException {
        try {
            UserPreferenceEntity entity = userPreferenceDao.queryForId(id);
            if (entity != null) {
                userPreferenceDao.delete(entity);
                return new UserPreferenceMapper().convertEntityToDomain(entity);
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }
}
