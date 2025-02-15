package com.example.lab9.repositories;

import com.example.lab9.entities.Book;

public class BookRepositoryJPA extends AbstractRepositoryJPA<Book, Integer> {
    @Override
    protected Class<Book> getEntityClass() {
        return Book.class;
    }

    @Override
    protected String getFindByNameQuery() {
        return "Book.findByTitle";
    }

    @Override
    protected String getFindByIdQuery() {
        return "Book.findById";
    }

}
