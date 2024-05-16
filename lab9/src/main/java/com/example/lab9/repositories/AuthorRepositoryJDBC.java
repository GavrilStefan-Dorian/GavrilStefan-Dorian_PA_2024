package com.example.lab9.repositories;

import com.example.lab9.entities.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthorRepositoryJDBC extends AbstractRepositoryJDBC<Author, Integer> {
    protected static final String STMT_INSERT = "INSERT INTO Authors (name) VALUES (?) ON CONFLICT DO NOTHING";
    protected static final String STMT_FIND_BY_NAME = "SELECT * FROM Authors WHERE name = ?";
    protected static final String STMT_FIND_BY_ID = "SELECT * FROM Authors WHERE id = ?";
    protected static final String STMT_SELECT_ALL = "SELECT * FROM Authors";

    @Override
    protected String getFindByIdQuery() {
        return STMT_FIND_BY_ID;
    }

    @Override
    protected String getFindByNameQuery() {
        return STMT_FIND_BY_NAME;
    }

    @Override
    protected String getFindAllQuery() {
        return STMT_SELECT_ALL;
    }

    @Override
    protected String getInsertQuery() {
        return STMT_INSERT;
    }

    @Override
    protected Author createFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Author(id, name);
    }

    @Override
    protected void setParametersForInsert(PreparedStatement pstmt, Author entity) throws SQLException {
        pstmt.setString(1, entity.getName());
    }

    @Override
    protected Integer getEntityId(Author entity) {
        return entity.getId();
    }
}
