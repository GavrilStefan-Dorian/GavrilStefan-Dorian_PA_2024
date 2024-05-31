//package org.example.Lab11.factories;
//
//import org.example.Lab11.entities.Author;
//import org.example.Lab11.entities.Book;
//import org.example.Lab11.entities.Genre;
//import org.example.Lab11.entities.ReadingList;
//import org.example.Lab11.repositories.*;
//
//public class JPAFactory extends AbstractFactory{
//
//    @Override
//    public Repository<Book, Integer> createBookRepository() {
//        return new BookRepositoryJPA();
//    }
//
//    @Override
//    public Repository<Author, Integer> createAuthorRepository() {
//        return new AuthorRepositoryJPA();
//    }
//
//    @Override
//    public Repository<Genre, Integer> createGenreRepository() {
//        return new GenreRepositoryJPA();
//    }
//
//    @Override
//    public Repository<ReadingList, Integer> createListRepository() {
//        return new ReadingListRepositoryJPA();
//    }
//}
