package com.example.lab9.entities;

import jakarta.persistence.*;


@Entity
@Table(name="genres",schema="public")
@NamedQueries({
        @NamedQuery(name = "Genre.findById", query = "SELECT g FROM Genre g WHERE g.id = :id"),
        @NamedQuery(name = "Genre.findByName", query = "SELECT g FROM Genre g WHERE g.name = :myName")
})

public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name", unique = true)
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Genre() {
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
