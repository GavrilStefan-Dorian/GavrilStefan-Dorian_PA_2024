package org.example;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class ReadingList {
//    private int id;
    private String name;
    private Date creationTimestamp;
    private Set<Book> books;

    public ReadingList(int id, String name, Date creationTimestamp) {
//        this.id = id;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
        this.books = new HashSet<>();
    }
    public void addBook(Book book) {
        books.add(book);
    }
}