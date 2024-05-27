package org.example;

import org.graph4j.GraphBuilder;
import org.graph4j.alg.coloring.eq.BacktrackEquitableColoring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PartitionerReadingLists {
    public void partitionBooks() {
        var graph = GraphBuilder.empty().buildGraph();

        var books = new BookDAO();
        try {
            List<Book> bookList = books.findAll();
            for (Book book : bookList) {
                graph.addVertex(book.getId());
            }
            for (Book book1 : bookList) {
                int id1 = book1.getId();
                for (Book book2 : bookList) {
                    if (!book1.equals(book2)) {
                        int id2 = book2.getId();
                        if(areRelatedByAuthor(id1, id2) || areRelatedByGenre(id1, id2)) {
                            System.out.println(book1.getTitle() + " " + book2.getTitle());
                            graph.addEdge(id1, id2);
                        }
                    }
                }
            }
            System.out.println(graph);
            BacktrackEquitableColoring coloring = new BacktrackEquitableColoring(graph);
            var solution = coloring.findColoring();
            System.out.println(solution.getColorClasses());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    private boolean areRelatedByAuthor(int bookId1, int bookId2) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                     "SELECT COUNT(*) FROM BookAuthors ba1 " +
                             "JOIN BookAuthors ba2 ON ba1.author_id = ba2.author_id " +
                             "WHERE ba1.book_id = ? AND ba2.book_id = ?")) {
            pstmt.setInt(1, bookId1);
            pstmt.setInt(2, bookId2);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    private boolean areRelatedByGenre(int bookId1, int bookId2) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                     "SELECT COUNT(*) FROM Books b1 " +
                             "JOIN Books b2 ON b1.genre = b2.genre " +
                             "WHERE b1.id = ? AND b2.id = ?")) {
            pstmt.setInt(1, bookId1);
            pstmt.setInt(2, bookId2);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
}






































