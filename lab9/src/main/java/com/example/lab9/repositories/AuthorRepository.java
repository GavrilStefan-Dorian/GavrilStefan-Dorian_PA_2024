package com.example.lab9.repositories;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;

public class AuthorRepository extends DataRepository<Author,Long> {
    protected Class<Author> getEntityClass() {
        return Author.class;
    }
}
