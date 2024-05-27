package com.example.lab9.factories;

import com.example.lab9.entities.Author;
import com.example.lab9.entities.Book;
import com.example.lab9.entities.Genre;
import com.example.lab9.entities.ReadingList;
import com.example.lab9.repositories.Repository;

public abstract class AbstractFactory {
    public  abstract Repository<Book, Integer> createBookRepository();
    public abstract Repository<Author, Integer> createAuthorRepository();
    public abstract Repository<Genre, Integer> createGenreRepository();
    public abstract Repository<ReadingList, Integer> createListRepository();
}