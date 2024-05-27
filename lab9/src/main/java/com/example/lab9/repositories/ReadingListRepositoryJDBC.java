package com.example.lab9.repositories;

import com.example.lab9.Database;
import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.ReadingList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadingListRepositoryJDBC extends AbstractRepositoryJDBC<ReadingList, Integer>{
    protected static final String STMT_INSERT = "INSERT INTO readinglists (name, creation_timestamp) VALUES (?, ?) ON CONFLICT DO NOTHING";
    protected static final String STMT_FIND_BY_NAME = "SELECT * FROM readinglists WHERE name = ?";
    protected static final String STMT_FIND_BY_ID = "SELECT * FROM readinglists WHERE id = ?";
    protected static final String STMT_SELECT_ALL = "SELECT * FROM readinglists";
    protected static final String STMT_INSERT_BOOK_LIST = "INSERT INTO list_books (list_id, book_id) VALUES (?, ?) ON CONFLICT DO NOTHING";

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
    protected ReadingList createFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        // Fetch authors associated with the book
        List<Book> books = fetchBooks(id);
        Timestamp creation_ts = rs.getTimestamp("creation_timestamp");
        var readingList = new ReadingList(name, creation_ts);
        readingList.setBooks(books);
        return readingList;
    }

    @Override
    protected void setParametersForInsert(PreparedStatement pstmt, ReadingList entity) throws SQLException {
        pstmt.setString(1, entity.getName());
        pstmt.setTimestamp(2, entity.getCreationTimestamp());
    }

    @Override
    protected Integer getEntityId(ReadingList entity) {
        return entity.getId();
    }

    private List<Book> fetchBooks(int listId) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT l.id, l.name FROM Books l JOIN list_books lb ON l.id = lb.book_id WHERE lb.list_id = ?")) {
            pstmt.setInt(1, listId);
            ResultSet rs = pstmt.executeQuery();
            var repo = new BookRepositoryJDBC();
            while (rs.next()) {
                books.add(repo.findById(rs.getInt("id")));
            }
        }
        return books;
    }

    public void createBookListRelation(int bookId, int listId) {
        try (PreparedStatement pstmt = getConnection().prepareStatement(STMT_INSERT_BOOK_LIST)) {
            pstmt.setInt(1, listId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("Book-List relation created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
