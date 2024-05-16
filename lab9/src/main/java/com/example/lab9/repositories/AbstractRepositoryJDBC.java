package com.example.lab9.repositories;

import com.example.lab9.Database;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepositoryJDBC<T, ID extends Serializable> implements Repository<T, ID> {

    protected abstract String getFindByIdQuery();
    protected abstract String getFindByNameQuery();
    protected abstract String getFindAllQuery();
    protected abstract String getInsertQuery();

    protected Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    protected abstract T createFromResultSet(ResultSet rs) throws SQLException;
    protected abstract void setParametersForInsert(PreparedStatement pstmt, T entity) throws SQLException;
    protected abstract ID getEntityId(T entity);

    @Override
    public void create(T entity) {
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            setParametersForInsert(pstmt, entity);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println(entity.getClass().getName() + " created with ID: " + id);
            } else {
                System.out.println("Error during creation of Book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T findById(ID id) {
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(getFindByIdQuery())) {
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T findByName(String name) {
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(getFindByNameQuery())) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(getFindAllQuery());
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                entities.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }
}
