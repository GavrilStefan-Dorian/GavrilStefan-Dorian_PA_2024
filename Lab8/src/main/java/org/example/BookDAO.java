package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    static final String STMT_INSERT = "INSERT INTO Books (title, language, publication_date, pages, genres) VALUES (?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";
    static final String STMT_SELECT_TITLE = "SELECT * FROM Books WHERE title = ?";
    static final String STMT_SELECT_ID = "SELECT * FROM Books WHERE id = ?";
    static final String STMT_SELECT_ALL = "SELECT * FROM Books";

    static final String STMT_INSERT_BOOK_AUTHORS = "INSERT INTO BookAuthors (book_id, author_id) VALUES (?, ?) ON CONFLICT DO NOTHING";

    public void create(Date pubDate, String title, String aName, String gName, String language, int pages) throws SQLException {
        try (Connection con = Database.getConnection()) {
            int bookId = -1;

            try (PreparedStatement pstmt = con.prepareStatement(STMT_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, title);
                pstmt.setString(2, language);
                pstmt.setDate(3, pubDate);
                pstmt.setInt(4, pages);
                pstmt.setString(5, gName);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    bookId = rs.getInt(1);
                    System.out.println("Book created with ID: " + bookId);
                } else {
                    System.out.println("Error during creation of Book");
                    return;
                }
            }

            String[] authorNames = aName.split("/");
            for (String name : authorNames) {
                try (PreparedStatement pstmt = con.prepareStatement(STMT_INSERT_BOOK_AUTHORS, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setInt(1, bookId);
                    pstmt.setInt(2, new AuthorDAO().findByName(name));
                    pstmt.executeUpdate();

                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        System.out.println("Book-Author relation created");
                    }
                }
            }
        }
    }

    public Book findByTitle(String title) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(STMT_SELECT_TITLE)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createBookFromResultSet(rs);
            }
            return null;
        }
    }

    public Book findById(int id) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(STMT_SELECT_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createBookFromResultSet(rs);
            }
            return null;
        }
    }
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(STMT_SELECT_ALL)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                books.add(createBookFromResultSet(rs));
            }
        }
        return books;
    }

    private Book createBookFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        // Fetch authors associated with the book
        List<Author> authors = fetchAuthorsForBook(id);
        String language = rs.getString("language");
        Date publication_date = rs.getDate("publication_date");
        int num_pages = rs.getInt("pages");
        String genre = rs.getString("genre");
        return new Book(id, title, authors, language, publication_date, num_pages, genre);
    }

    private List<Author> fetchAuthorsForBook(int bookId) throws SQLException {
        List<Author> authors = new ArrayList<>();
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT a.id, a.name FROM Authors a JOIN BookAuthors ba ON a.id = ba.author_id WHERE ba.book_id = ?")) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int authorId = rs.getInt("id");
                String authorName = rs.getString("name");
                authors.add(new Author(authorId, authorName));
            }
        }
        return authors;
    }
}
