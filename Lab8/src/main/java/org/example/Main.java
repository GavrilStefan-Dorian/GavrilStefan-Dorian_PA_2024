package org.example;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException {
//        createTables();
        importData();
//        PartitionerReadingLists prl = new PartitionerReadingLists();
//        prl.partitionBooks();
    }

    private static void createTables() throws SQLException {
        try (Statement statement = Database.getConnection().createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE Genres (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) UNIQUE
                    );
                                        
                    CREATE TABLE Books (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255) UNIQUE,
                        language VARCHAR(50),
                        publication_date DATE,
                        pages INT,
                        genre VARCHAR(100),
                        FOREIGN KEY (genre) REFERENCES Genres(name)
                    );

                    CREATE TABLE Authors (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) UNIQUE
                    );

                    CREATE TABLE BookAuthors (
                        book_id INT,
                        author_id INT,
                        PRIMARY KEY (book_id, author_id),
                        FOREIGN KEY (book_id) REFERENCES Books(id),
                        FOREIGN KEY (author_id) REFERENCES Authors(id)
                    );
                    """);
        }
    }

    private static void importData() throws SQLException {
        var authors = new AuthorDAO();
        var genres = new GenreDAO();
        var books = new BookDAO();

        var data = DatasetTool.getData("books.csv");
        assert data != null;
        for (String[] row : data) {
            if (row.length == 13) {
                if (row[0].compareTo("bookID") == 0)
                    continue;

                authors.create(row[3]);
                genres.create(row[2]);

                try {
                    // Parse the date from the row
                    java.util.Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse(row[11]);
                    // Create a java.sql.Date object
                    java.sql.Date publicationDate = new java.sql.Date(parsedDate.getTime());

                    // Proceed with creating the book
                    books.create(publicationDate, row[1], row[3], row[2], row[7], Integer.parseInt(row[8]));
                } catch (ParseException e) {
                    // Skip processing this row due to invalid date format
                    System.err.println("Skipping row due to invalid date format: " + row[11]);
                }
            }
        }
    }
}
