//package com.example.weeklyplanner.repository;
//
//import com.example.weeklyplanner.domain.model.Task;
//import com.example.weeklyplanner.persistence.db.DatabaseConfigurator;
//import com.example.weeklyplanner.persistence.entity.TaskEntity;
//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.dao.DaoManager;
//import com.j256.ormlite.jdbc.JdbcConnectionSource;
//import com.j256.ormlite.stmt.QueryBuilder;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TaskRepository implements BaseRepository<Task> {
//    private final Dao<TaskEntity, Integer> taskDao;
//
//    public TaskRepository() {
//        try (JdbcConnectionSource jcs = DatabaseConfigurator.getDatabaseConnectionSource()) {
//            taskDao = DaoManager.createDao(jcs, TaskEntity.class);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public List<Task> getAll() {
//        QueryBuilder<TaskEntity, Integer> queryBuilder = taskDao.queryBuilder();
//        queryBuilder.orderBy("id", false);
//        try {
//            List<TaskEntity> rows = queryBuilder.query();
//            List<Task> tasks = new ArrayList<>();
////            for (TaskEntity row : rows) {
////            }
//            return tasks;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public Task getOne(Integer id) {
//        return null;
//    }
//
//    @Override
//    public Task add(Task entity) {
//        return null;
//    }
//
//    @Override
//    public Task update(Task entity) {
//        return null;
//    }
//
//    @Override
//    public Task delete(Integer id) {
//        return null;
//    }
//}
