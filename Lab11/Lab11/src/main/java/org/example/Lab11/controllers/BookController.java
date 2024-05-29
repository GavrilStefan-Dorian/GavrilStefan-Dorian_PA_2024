package org.example.Lab11.controllers;

import org.example.Lab11.entities.Author;
import org.example.Lab11.entities.Book;
import org.example.Lab11.entities.Genre;
import org.example.Lab11.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private Repository<Book,Integer> bookRepo;

    @GetMapping
    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    @GetMapping("/count")
    public int countBooks(){
        return bookRepo.findAll().size();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") int id){
        return bookRepo.findById(id);
    }


    @PostMapping
    public ResponseEntity<String> createBook(@RequestParam String title){
      Book newBook=new Book();
      newBook.setTitle(title);
      bookRepo.create(newBook);
      return new ResponseEntity<>("Book created with success with id: "+newBook.getId(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/obj", consumes = "application/json")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        bookRepo.create(book);
        return new ResponseEntity<>("Book created successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(
            @PathVariable int id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) List<Author> authors,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) Integer pages,
            @RequestParam(required = false) Date publicationDate) {

        Book book = bookRepo.findById(id);
        if (book == null) {
            return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
        }

        boolean updated = false;

        if (title != null) {
            book.setTitle(title);
            updated = true;
        }

        if (language != null) {
            book.setLanguage(language);
            updated = true;
        }

        if (authors != null) {
            book.setAuthors(authors);
            updated = true;
        }

        if (genre != null) {
            book.setGenreName(genre);
            updated = true;
        }

        if (pages != null) {
            book.setNumberOfPages(pages);
            updated = true;
        }

        if (publicationDate != null) {
            book.setPublicationDate(publicationDate);
            updated = true;
        }

        if (updated) {
            bookRepo.update(book);
            return new ResponseEntity<>("Book updated successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No update parameters provided!", HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateBookTitle(@PathVariable int id,@RequestParam String title){
//        Book book=bookRepo.findById(id);
//        if(book==null){
//            return new ResponseEntity<>("Book not found!",HttpStatus.NOT_FOUND);
//        }
//        book.setTitle(title);
//        bookRepo.update(book);
//        return new ResponseEntity<>("Book updated with succeess!",HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public  ResponseEntity<String> updateBookLang(@PathVariable int id, @RequestParam String language){
//        Book book=bookRepo.findById(id);
//        if(book==null){
//            return new ResponseEntity<>("Book not found!",HttpStatus.NOT_FOUND);
//        }
//        book.setLanguage(language);
//        bookRepo.update(book);
//        return new ResponseEntity<>("Book updated with succeess!",HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateBookAuthors(@PathVariable int id,@RequestParam List<Author> authors){
//        Book book=bookRepo.findById(id);
//        if(book==null){
//            return new ResponseEntity<>("Book not found!",HttpStatus.NOT_FOUND);
//        }
//        book.setAuthors(authors);
//        bookRepo.update(book);
//        return new ResponseEntity<>("Book updated with success!",HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateBookGenre(@PathVariable int id,@RequestParam Genre genre){
//        Book book=bookRepo.findById(id);
//        if(book==null){
//            return new ResponseEntity<>("Book not found!",HttpStatus.NOT_FOUND);
//        }
//        book.setGenreName(genre);
//        bookRepo.update(book);
//        return new ResponseEntity<>("Book updated with success!",HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateBookNoPages(@PathVariable int id,@RequestParam int pages){
//        Book book=bookRepo.findById(id);
//        if(book==null){
//            return new ResponseEntity<>("Book not found!",HttpStatus.NOT_FOUND);
//        }
//        book.setNumberOfPages(pages);
//        bookRepo.update(book);
//        return new ResponseEntity<>("Book updated with success!",HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateBookPublicationDate(@PathVariable int id,@RequestParam Date publicationDate){
//        Book book=bookRepo.findById(id);
//        if(book==null){
//            return new ResponseEntity<>("Book not found!",HttpStatus.NOT_FOUND);
//        }
//        book.setPublicationDate(publicationDate);
//        bookRepo.update(book);
//        return new ResponseEntity<>("Book updated with success!",HttpStatus.OK);
//    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id){
        Book book=bookRepo.findById(id);
        if(book==null){
            return new ResponseEntity<>("Book not found!",HttpStatus.NOT_FOUND);
        }
        bookRepo.delete(id);
        return new ResponseEntity<>("Book was deleted!",HttpStatus.OK);
    }
}
