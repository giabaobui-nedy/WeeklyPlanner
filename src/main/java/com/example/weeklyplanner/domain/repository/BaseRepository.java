package com.example.weeklyplanner.domain.repository;

import java.sql.SQLException;
import java.util.List;

public interface BaseRepository<T> {
    public List<T> getAll() throws SQLException;

    public T getOne(Integer id) throws SQLException;

    public T add(T entity) throws SQLException;

    public T update(T entity) throws SQLException;

    public T delete(Integer id) throws SQLException;
}
