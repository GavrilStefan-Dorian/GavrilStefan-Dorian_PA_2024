package com.example.lab9.repositories;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;

public class AuthorRepositoryJPA extends AbstractRepositoryJPA<Author, Integer>{
    @Override
    protected String getFindByIdQuery() {
        return "Author.findById";
    }

    @Override
    protected String getFindByNameQuery() {
        return "Author.findByName";
    }

    @Override
    protected Class<Author> getEntityClass() {
        return Author.class;
    }
}
