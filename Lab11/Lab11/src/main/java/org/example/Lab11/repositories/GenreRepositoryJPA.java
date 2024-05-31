
package org.example.Lab11.repositories;

import org.example.Lab11.entities.AppUser;
import org.example.Lab11.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepositoryJPA extends JpaRepository<Genre, Integer> {
    public Genre findByName(String name);

}























//package org.example.Lab11.repositories;
//
//import org.example.Lab11.entities.Genre;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class GenreRepositoryJPA extends AbstractRepositoryJPA<Genre, Integer>{
//
//    @Override
//    protected Class<Genre> getEntityClass() {
//        return Genre.class;
//    }
//
//    @Override
//    protected String getFindByNameQuery() {
//        return "Genre.findByName";
//    }
//
//    @Override
//    protected String getFindByIdQuery() {
//        return "Book.findById";
//    }
//}
