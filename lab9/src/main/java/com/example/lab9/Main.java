package com.example.lab9;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;
import com.example.lab9.repositories.AuthorRepository;
import com.example.lab9.repositories.BookRepository;
import com.example.lab9.repositories.GenreRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) throws ParseException {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("log4j.properties")) {
            if (inputStream != null) {
                LogManager.getLogManager().readConfiguration(inputStream);
            } else {
                System.err.println("Could not find logging.properties file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Lab9PU");
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        BookRepository bookRepository = new BookRepository();
        AuthorRepository authorRepository = new AuthorRepository();
        GenreRepository genreRepository = new GenreRepository();

        Book book1 = new Book();
        book1.setTitle("Random");
        book1.setLanguage("eng");

        java.util.Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse("7/01/2010");
        java.sql.Date publicationDate = new java.sql.Date(parsedDate.getTime());

        book1.setPublicationDate(publicationDate);
        book1.setNumberOfPages(370);
        book1.setGenreName(new Genre(3, "Fiction"));
        bookRepository.save(book1);

        Author author1 = new Author();
        author1.setName("Craigss Robertson");
        authorRepository.save(author1);

        emf.close();
    }
}
