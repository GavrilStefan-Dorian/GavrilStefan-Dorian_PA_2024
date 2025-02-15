package org.example.Lab11.repositories;

import org.example.Lab11.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepositoryJPA extends JpaRepository<Author, Integer> {
    public Author findByName(String name);
}
























//package org.example.Lab11.repositories;
//
//import org.example.Lab11.entities.Author;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class AuthorRepositoryJPA extends AbstractRepositoryJPA<Author, Integer>{
//    @Override
//    protected String getFindByIdQuery() {
//        return "Author.findById";
//    }
//
//    @Override
//    protected String getFindByNameQuery() {
//        return "Author.findByName";
//    }
//
//    @Override
//    protected Class<Author> getEntityClass() {
//        return Author.class;
//    }
//}
