package org.example.Lab11.repositories;

import org.example.Lab11.entities.Book;
import org.springframework.stereotype.Repository;

@Repository
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
