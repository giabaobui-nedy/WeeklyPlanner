package com.example.weeklyplanner.persistence.repository;

import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.domain.repository.TaskRepository;
import com.example.weeklyplanner.persistence.db.DatabaseConfigurator;
import com.example.weeklyplanner.persistence.entity.TaskEntity;
import com.example.weeklyplanner.persistence.mapper.TaskMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {
    private final Dao<TaskEntity, Integer> taskDao;

    public TaskRepositoryImpl() {
        try (JdbcConnectionSource jcs = DatabaseConfigurator.getDatabaseConnectionSource()) {
            taskDao = DaoManager.createDao(jcs, TaskEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> getAll() throws SQLException {
        QueryBuilder<TaskEntity, Integer> queryBuilder = taskDao.queryBuilder();
        queryBuilder.orderBy("id", false);
        try {
            List<TaskEntity> rows = queryBuilder.query();
            TaskMapper taskMapper = new TaskMapper();
            return taskMapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task update(Task entity) throws SQLException {
        return null;
    }

    @Override
    public Task delete(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Task getOne(Integer id) throws SQLException {
        try {
            TaskEntity taskEntity = taskDao.queryForId(id);
            return new TaskMapper().convertEntityToDomain(taskEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Task add(Task entity) throws SQLException {
        TaskEntity taskEntity = new TaskMapper().convertDomainToEntity(entity);
        taskDao.create(taskEntity);
        return new TaskMapper().convertEntityToDomain(taskEntity);
    } 
}
