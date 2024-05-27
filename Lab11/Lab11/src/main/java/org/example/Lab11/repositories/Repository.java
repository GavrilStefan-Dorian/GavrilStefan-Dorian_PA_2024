package org.example.Lab11.repositories;

import java.util.List;

public interface Repository<T, ID> {
    void create(T entity);
    void delete(ID id);
    T findById(ID id);
    T findByName(String name);
    List<T> findAll();
}
