package com.example.lab9.entities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name="readinglists",schema="public")
@NamedQueries({
        @NamedQuery(name = "ReadingList.findById", query = "SELECT l FROM ReadingList l WHERE l.id = :id"),
        @NamedQuery(name = "ReadingList.findByName", query = "SELECT l FROM ReadingList l WHERE l.name = :myName")
})
public class ReadingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="creation_timestamp", nullable = false)
    private Timestamp creationTimestamp;

    @ManyToMany
    @JoinTable(
            name = "list_books",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public ReadingList(String name, Timestamp creationTimestamp) {
//        this.id = id;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
        this.books = new ArrayList<>();
    }
    public void addBook(Book book) {
        books.add(book);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}