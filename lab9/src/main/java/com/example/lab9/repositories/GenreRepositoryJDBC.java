package com.example.lab9.repositories;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GenreRepositoryJDBC extends AbstractRepositoryJDBC<Genre, Integer>{
    protected static final String STMT_INSERT = "INSERT INTO Genres (name) VALUES (?) ON CONFLICT DO NOTHING";
    protected static final String STMT_FIND_BY_NAME = "SELECT * FROM Genres WHERE name = ?";
    protected static final String STMT_FIND_BY_ID = "SELECT * FROM Genres WHERE id = ?";
    protected static final String STMT_SELECT_ALL = "SELECT * FROM Genres";

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
    protected Genre createFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }

    @Override
    protected void setParametersForInsert(PreparedStatement pstmt, Genre entity) throws SQLException {
        pstmt.setString(1, entity.getName());
    }

    @Override
    protected Integer getEntityId(Genre entity) {
        return entity.getId();
    }
}
