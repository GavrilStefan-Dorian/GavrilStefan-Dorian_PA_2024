package org.example.Lab11.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="books", schema="public")
//@NamedQueries({
//        @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :myId"),
//        @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :myName,'%')")
//})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @JsonProperty("id")
    private int id;

    @Column(name="title", unique = true)
    @JsonProperty("title")
    private String title;

    @Column(name="pages")
    private int numberOfPages;

    @Column(name="publication_date")
    private Date publicationDate;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "bookauthors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @Column(name="language")
    private String language;

    @JoinColumn(name="genre_id")
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Genre genre;

    public Book() {
    }

    public Book(int id, String title, int numberOfPages, Date publicationDate, List<Author> authors, String language, Genre genre) {
        this.id = id;
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.publicationDate = publicationDate;
        this.authors = authors;
        this.language = language;
        this.genre = genre;
    }

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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}

//package org.example.Lab11.entities;
//
//import jakarta.persistence.*;
//
//import java.sql.Date;
//import java.util.List;
//
//
//@Entity
//@Table(name="books", schema="public")
//@NamedQueries({
//        @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :myId"),
//        @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :myName,'%')")
//})
//public class Book {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="id")
//    private int id;
//
//    @Column(name="title", unique = true)
//    private String title;
//
//    @Column(name="pages")
//    private int numberOfPages;
//
//    @Column(name="publication_date")
//    private Date publicationDate;
//
//    @ManyToMany
//    @JoinTable(
//            name = "book_authors",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "author_id"))
//    private List<Author> authors;
//
//    @Column(name="language")
//    private String language;
//
//    @ManyToOne
//    @JoinColumn(name="genres", referencedColumnName = "name")
//    private Genre genreName;
//
//    public Book(int id, String title, List<Author> authors, String language, Date publication_date, int num_pages, Genre genre) {
//        this.id = id;
//        this.title = title;
//        this.authors = authors;
//        this.language = language;
//        this.publicationDate = publication_date;
//        this.numberOfPages = num_pages;
//        this.genreName = genre;
//    }
//
//    public Book() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public int getNumberOfPages() {
//        return numberOfPages;
//    }
//
//    public void setNumberOfPages(int numberOfPages) {
//        this.numberOfPages = numberOfPages;
//    }
//
//    public Date getPublicationDate() {
//        return publicationDate;
//    }
//
//    public void setPublicationDate(Date publicationDate) {
//        this.publicationDate = publicationDate;
//    }
//
//    public List<Author> getAuthors() {
//        return authors;
//    }
//
//    public void setAuthors(List<Author> authors) {
//        this.authors = authors;
//    }
//
//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public Genre getGenreName() {
//        return genreName;
//    }
//
//    public void setGenreName(Genre genreName) {
//        this.genreName = genreName;
//    }
//}
