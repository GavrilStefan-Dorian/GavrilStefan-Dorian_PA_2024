package org.example;

import java.sql.*;

public class GenreDAO {
    static final String STMT_INSERT = "INSERT INTO Genres (name) VALUES (?) ON CONFLICT DO NOTHING";
    static final String STMT_SELECT_ID = "SELECT id FROM Genres WHERE name = ?";
    static final String STMT_SELECT_NAME = "SELECT name FROM Genres WHERE id = ?";

    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        Integer genreId = findByName(name);

        if (genreId == null) {
            try (PreparedStatement pstmt = con.prepareStatement(
                    STMT_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("Genre created with ID: " + rs.getInt(1));
                }
            }
        } else {
            System.out.println("Genre already exists with ID: " + genreId);
        }

        con.close();
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

    public String findById(int id) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(STMT_SELECT_NAME)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
            return null;
        }
    }
}
