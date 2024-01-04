package com.dev.books.controllers;

import com.dev.books.domain.Book;
import com.dev.books.domain.BookEntity;
import com.dev.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);
        final Book savedBook = bookService.create(book);
        final ResponseEntity<Book> response = new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> readBook(@PathVariable final String isbn) {
        final Optional<Book> foundBook = bookService.findById(isbn);

        return foundBook.map( book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));

    }
}
