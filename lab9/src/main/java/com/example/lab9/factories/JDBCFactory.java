package com.example.lab9.factories;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;
import com.example.lab9.repositories.*;

public class JDBCFactory extends AbstractFactory{

    @Override
    public Repository<Book, Integer> createBookRepository() {
        return new BookRepositoryJDBC();
    }

    @Override
    public Repository<Author, Integer> createAuthorRepository() {
        return new AuthorRepositoryJDBC();
    }

    @Override
    public Repository<Genre, Integer> createGenreRepository() {
        return new GenreRepositoryJDBC();
    }
}
