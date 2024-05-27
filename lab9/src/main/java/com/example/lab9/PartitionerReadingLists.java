package com.example.lab9;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;
import com.example.lab9.entities.ReadingList;
import com.example.lab9.factories.AbstractFactory;
import com.example.lab9.repositories.ReadingListRepositoryJDBC;
import org.graph4j.GraphBuilder;
import org.graph4j.alg.coloring.eq.BacktrackEquitableColoring;

import java.sql.*;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
public class PartitionerReadingLists {
    AbstractFactory factory;
    EntityManager entityManager;

    public PartitionerReadingLists(AbstractFactory factory) {
        this.factory = factory;
        this.entityManager = Database.getInstance().getEntityManager();
    }

    public void partitionBooks() {
        var graph = GraphBuilder.empty().buildGraph();

        var books = factory.createBookRepository();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            List<Book> bookList = books.findAll();
            for (Book book : bookList) {
                graph.addVertex(book.getId());
            }
            for (Book book1 : bookList) {
                int id1 = book1.getId();
                for (Book book2 : bookList) {
                    if (!book1.equals(book2)) {
                        int id2 = book2.getId();
                        if (areRelatedByAuthor(id1, id2) || areRelatedByGenre(id1, id2)) {
                            System.out.println(book1.getTitle() + " |SHARES AUTHOR/GENRE WITH| " + book2.getTitle());
                            graph.addEdge(id1, id2);
                        }
                    }
                }
            }
            System.out.println(graph);
            BacktrackEquitableColoring coloring = new BacktrackEquitableColoring(graph);
            var solution = coloring.findColoring();

            for (var colorClass : solution.getColorClasses().keySet()) {
                ReadingList newList = new ReadingList("ReadingList" + colorClass, new Timestamp(System.currentTimeMillis()));
                entityManager.persist(newList);

                for (var bookId : solution.getColorClasses().get(colorClass)) {
                    Book book = books.findById(bookId);
                    newList.addBook(book);
                }
                entityManager.merge(newList);
            }

            transaction.commit();
            System.out.println(solution.getColorClasses());
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    private boolean areRelatedByAuthor(int bookId1, int bookId2) throws SQLException {
        var books = factory.createBookRepository();
        List<Author> authors1 = books.findById(bookId1).getAuthors();
        List<Author> authors2 = books.findById(bookId2).getAuthors();

        for (Author author1 : authors1) {
            for (Author author2 : authors2)
                if (author1.getId() == author2.getId())
                    return true;
        }
        return false;
    }

    private boolean areRelatedByGenre(int bookId1, int bookId2) throws SQLException {
        var books = factory.createBookRepository();
        var genre1 = books.findById(bookId1).getGenreName();
        var genre2 = books.findById(bookId2).getGenreName();

        return genre1.getId() == genre2.getId();
    }
}