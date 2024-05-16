//package com.example.lab9.repositories;
//
//import com.example.lab9.entities.Book;
//
//import java.util.List;
//
//public class BookRepository extends DataRepository<Book, Long> {
//    protected Class<Book> getEntityClass() {
//        return Book.class;
//    }
//
//    public List<Book> findBooksWithTitle(String title) {
//        return getEntityManager()
//                .createNamedQuery("Book.findByTitle", Book.class)
//                .setParameter("title", title)
//                .getResultList();
//    }
//}