package com.example.lab9.repositories;

import java.util.List;

public interface Repository<T, ID> {
    void create(T entity);
    T findById(ID id);
    T findByName(String name);
    List<T> findAll();
}
