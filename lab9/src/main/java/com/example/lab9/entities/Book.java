package com.example.lab9.entities;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;


@Entity
@Table(name="books", schema="public")
@NamedQueries({
        @NamedQuery(name = "Book.findById", query = "SELECT id FROM Book b WHERE b.id = :myId"),
        @NamedQuery(name = "Book.findByTitle", query = "SELECT title FROM Book b WHERE b.title LIKE CONCAT('%', :title,'%')")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title", unique = true)
    private String title;

    @Column(name="pages")
    private int numberOfPages;

    @Column(name="publication_date")
    private Date publicationDate;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @Column(name="language")
    private String language;

    @ManyToOne
    @JoinColumn(name="genres", referencedColumnName = "name")
    private Genre genreName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Genre getGenreName() {
        return genreName;
    }

    public void setGenreName(Genre genreName) {
        this.genreName = genreName;
    }
}
