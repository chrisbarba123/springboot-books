package com.dev.books.controllers;

import com.dev.books.domain.Book;
import com.dev.books.domain.BookEntity;
import com.dev.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

//    @PutMapping(path = "/books/{isbn}")
//    public ResponseEntity<Book> createBook(@PathVariable final String isbn, @RequestBody final Book book) {
//        book.setIsbn(isbn);
//        final Book savedBook = bookService.create(book);
//        final ResponseEntity<Book> response = new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//        return response;
//    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> readBook(@PathVariable final String isbn) {
        final Optional<Book> foundBook = bookService.findById(isbn);

        return foundBook.map( book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> readAlLBooks() {
        final List<Book> foundBooks = bookService.findAllBooks();

        return new ResponseEntity<>(foundBooks, HttpStatus.OK);
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createUpdateBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);

        final boolean isBookExists = bookService.isBookExists(book);
        final Book savedBook = bookService.save(book);

        if (isBookExists) {
            return new ResponseEntity<Book>(savedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
        }
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable final String isbn) {
        bookService.deleteBookById(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
