package org.example.Lab11.repositories;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;

import java.util.List;
public interface Repository<T, ID> {
    void create(T entity);
    void delete(ID id);
    void update(T entity);
    T findById(ID id);
    T findByName(String name);
    List<T> findAll();
}
