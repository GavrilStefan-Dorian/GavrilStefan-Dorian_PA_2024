package org.example;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            createTables();
            AuthorDAO authorDAO = new AuthorDAO();
            authorDAO.create("Gavril Stefan");

            Database.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            Database.rollback();
        }
    }

    private static void createTables() throws SQLException {
        try (Statement statement = Database.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Author (id SERIAL PRIMARY KEY, name VARCHAR(255))");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Book " +
                    "(id SERIAL PRIMARY KEY," +
                    "title VARCHAR(255)," +
                    "author_id INT," +
                    "language VARCHAR(50)," +
                    "publication_date DATE," +
                    "number_of_pages INT," +
                    "FOREIGN KEY (author_id) REFERENCES Author(id))");
        }
    }
}
