package org.example;

import java.sql.*;

public class AuthorDAO {
    static final String STMT_INSERT = "INSERT INTO Authors (name) VALUES (?) ON CONFLICT DO NOTHING";
    static final String STMT_SELECT_ID = "SELECT id FROM Authors WHERE name = ?";
    static final String STMT_SELECT_NAME = "SELECT name FROM Authors WHERE id = ?";

    public void create(String name) throws SQLException {
        String[] authorNames = name.split("/");

        try (Connection con = Database.getConnection()) {
            for(String aName : authorNames) {
                if (findByName(aName) == null) {
                    try (PreparedStatement pstmt = con.prepareStatement(
                            STMT_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, aName.trim());
                        pstmt.executeUpdate();

                        ResultSet rs = pstmt.getGeneratedKeys();
                        if (rs.next()) {
                            System.out.println("Author created with ID: " + rs.getInt(1));
                        }
                    }
                } else {
                    System.out.println("Author '" + aName + "' already exists in the database.");
                }
            }
        }
    }


    public Integer findByName(String name) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(STMT_SELECT_ID)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return null;
        }
    }

    public Author findById(int id) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(STMT_SELECT_NAME)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Author(id, rs.getString("name"));
            }
            return null;
        }
    }
}
