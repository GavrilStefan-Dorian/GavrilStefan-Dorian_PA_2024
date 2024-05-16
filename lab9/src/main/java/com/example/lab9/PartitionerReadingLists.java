package com.example.lab9;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;
import com.example.lab9.factories.AbstractFactory;
import org.graph4j.GraphBuilder;
import org.graph4j.alg.coloring.eq.BacktrackEquitableColoring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PartitionerReadingLists {
    AbstractFactory factory;

    public PartitionerReadingLists(AbstractFactory factory) {
        this.factory = factory;
    }

    public void partitionBooks() {
        var graph = GraphBuilder.empty().buildGraph();

        var books = factory.createBookRepository();
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
                            System.out.println(book1.getTitle() + " |SHARES AUTHOR/GENRE WITH| " + book2.getTitle());
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
        var books= factory.createBookRepository();
        List<Author> authors1 = books.findById(bookId1).getAuthors();
        List<Author> authors2 = books.findById(bookId2).getAuthors();

        for(Author author1: authors1) {
            for(Author author2 : authors2)
                if(author1.getId() == author2.getId())
                    return true;
        }
        return false;
    }

    private boolean areRelatedByGenre(int bookId1, int bookId2) throws SQLException {
        var books= factory.createBookRepository();
        Genre genre1 = books.findById(bookId1).getGenreName();
        Genre genre2 = books.findById(bookId2).getGenreName();

        return genre1.getId() == genre2.getId();

    }
}
