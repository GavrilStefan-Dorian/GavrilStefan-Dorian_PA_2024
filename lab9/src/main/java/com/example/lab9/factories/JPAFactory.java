package com.example.lab9.factories;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;
import com.example.lab9.entities.ReadingList;
import com.example.lab9.repositories.*;

public class JPAFactory extends AbstractFactory{

    @Override
    public Repository<Book, Integer> createBookRepository() {
        return new BookRepositoryJPA();
    }

    @Override
    public Repository<Author, Integer> createAuthorRepository() {
        return new AuthorRepositoryJPA();
    }

    @Override
    public Repository<Genre, Integer> createGenreRepository() {
        return new GenreRepositoryJPA();
    }

    @Override
    public Repository<ReadingList, Integer> createListRepository() {
        return new ReadingListRepositoryJPA();
    }
}
