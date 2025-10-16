package com.example.weeklyplanner.domain.repository;

import java.util.List;

public interface BaseRepository<T> {
    public List<T> getAll();

    public T getOne(Integer id);

    public T add(T entity);

    public T update(T entity);

    public T delete(Integer id);
}
