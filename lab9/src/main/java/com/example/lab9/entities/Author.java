package com.example.lab9.entities;

import jakarta.persistence.*;

@Entity
@Table(name="authors", schema ="public")
@NamedQueries({
        @NamedQuery(name = "Author.findById", query = "SELECT a FROM Author a WHERE a.id = :myId"),
        @NamedQuery(name = "Author.findByName", query = "SELECT a FROM Author a WHERE a.name LIKE CONCAT('%', :myName,'%')")
})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name", unique = true)
    private String name;

    public Author(int authorId, String authorName) {
        id = authorId;
        name = authorName;
    }

    public Author() {

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
}
