package org.example;

import java.sql.*;

public class AuthorDAO {
    static final String STMT_INSERT = "INSERT INTO authors (name) VALUES (?)";
    static final String STMT_SELECT_ID = "SELECT id FROM authors WHERE name = ?";
    static final String STMT_SELECT_NAME = "SELECT name FROM authors WHERE id = ?";

    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement(
                STMT_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("Author created with ID: " + rs.getInt(1));
            }
        }
        Database.commit();
    }

    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                STMT_SELECT_ID)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return null;
        }
    }
    public Author findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                STMT_SELECT_NAME)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Author(id, rs.getString("name"));
            }
            return null;
        }
    }
}
