package org.example;

import java.util.Date;
import java.util.List;

public class Book {
    private final int id;
    private final String title;
    private final List<Author> authors;
    private final String language;
    private final Date  publication_date;
    private final int num_pages;
    private final String genreName;

    public Book(int id, String title, List<Author> authors, String language, Date publication_date, int num_pages, String genre) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.language = language;
        this.publication_date = publication_date;
        this.num_pages = num_pages;
        this.genreName = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getLanguage() {
        return language;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public String getGenreName() {
        return genreName;
    }
}
