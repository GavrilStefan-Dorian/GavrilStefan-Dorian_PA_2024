package org.example.Lab11.repositories;

import org.example.Lab11.entities.Genre;

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
