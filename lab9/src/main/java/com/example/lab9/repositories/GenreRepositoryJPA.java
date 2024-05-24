package com.example.lab9.repositories;

import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;

public class GenreRepositoryJPA extends AbstractRepositoryJPA<Genre, Integer>{

    @Override
    protected Class<Genre> getEntityClass() {
        return Genre.class;
    }

    @Override
    protected String getFindByNameQuery() {
        return "Genre.findByName";
    }

    @Override
    protected String getFindByIdQuery() {
        return "Book.findById";
    }
}
