package com.example.lab9;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;
import com.example.lab9.factories.AbstractFactory;
import com.example.lab9.factories.JDBCFactory;
import com.example.lab9.factories.JPAFactory;
import com.example.lab9.repositories.Repository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {

        Properties appProps = new Properties();
        appProps.load(new FileInputStream("src//main//resources//app.properties"));

        AbstractFactory factory;
        switch(appProps.getProperty("data.access.type")) {
            case "jpa":
                factory = new JPAFactory();
                break;
            case "jdbc":
            default:
                factory = new JDBCFactory();
                break;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Lab9PU");
//        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
//        Statistics statistics = sessionFactory.getStatistics();
//        statistics.setStatisticsEnabled(true);

//        Repository<Book, Integer> bookRepository = factory.createBookRepository();
//        Repository<Author, Integer> authorRepository = factory.createAuthorRepository();
//        Repository<Genre, Integer> genreRepository = factory.createGenreRepository();
//
//
//        Book book1 = new Book();
//        book1.setTitle("Random5");
//        book1.setLanguage("eng");
//
//        java.util.Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse("7/01/2010");
//        java.sql.Date publicationDate = new java.sql.Date(parsedDate.getTime());
//
//        book1.setPublicationDate(publicationDate);
//        book1.setNumberOfPages(370);
//        book1.setGenreName(new Genre(3, "Fiction"));
//        bookRepository.create(book1);
//
//        Author author1 = new Author();
//        author1.setName("Craigs5 Robertson");
//        authorRepository.create(author1);
//
//        PartitionerReadingLists partitionerReadingLists = new PartitionerReadingLists(factory);
//        partitionerReadingLists.partitionBooks();

        ChocoSolver chocoSolver = new ChocoSolver(factory);
        chocoSolver.findBookSet(30, 3);
        emf.close();
    }
}
