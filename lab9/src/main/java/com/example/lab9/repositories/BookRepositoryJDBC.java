package com.example.lab9.repositories;

import com.example.lab9.Database;
import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryJDBC extends AbstractRepositoryJDBC<Book, Integer>{
    protected static final String STMT_INSERT = "INSERT INTO Books (title, language, publication_date, pages, genres) VALUES (?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";
    protected static final String STMT_FIND_BY_NAME = "SELECT * FROM Books WHERE title = ?";
    protected static final String STMT_FIND_BY_ID = "SELECT * FROM Books WHERE id = ?";
    protected static final String STMT_SELECT_ALL = "SELECT * FROM Books";
    protected static final String STMT_INSERT_BOOK_AUTHORS = "INSERT INTO BookAuthors (book_id, author_id) VALUES (?, ?) ON CONFLICT DO NOTHING";

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
    protected Book createFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        // Fetch authors associated with the book
        List<Author> authors = fetchAuthorsForBook(id);
        String language = rs.getString("language");
        Date publication_date = rs.getDate("publication_date");
        int num_pages = rs.getInt("pages");
        String genre = rs.getString("genres");
        return new Book(id, title, authors, language, publication_date, num_pages, new GenreRepositoryJDBC().findByName(genre));
    }

    @Override
    protected void setParametersForInsert(PreparedStatement pstmt, Book entity) throws SQLException {
        pstmt.setString(1, entity.getTitle());
        pstmt.setString(2, entity.getLanguage());
        pstmt.setDate(3, entity.getPublicationDate());
        pstmt.setInt(4, entity.getNumberOfPages());
        pstmt.setString(5, entity.getGenreName().getName());
    }

    @Override
    protected Integer getEntityId(Book entity) {
        return entity.getId();
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

    public void createBookAuthorRelation(int bookId, int authorId) {
        try (PreparedStatement pstmt = getConnection().prepareStatement(STMT_INSERT_BOOK_AUTHORS)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, authorId);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("Book-Author relation created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
