package org.example.Lab11.factories;

import org.example.Lab11.entities.Author;
import org.example.Lab11.entities.Book;
import org.example.Lab11.entities.Genre;
import org.example.Lab11.entities.ReadingList;
import org.example.Lab11.repositories.Repository;

public abstract class AbstractFactory {
    public  abstract Repository<Book, Integer> createBookRepository();
    public abstract Repository<Author, Integer> createAuthorRepository();
    public abstract Repository<Genre, Integer> createGenreRepository();
    public abstract Repository<ReadingList, Integer> createListRepository();
}