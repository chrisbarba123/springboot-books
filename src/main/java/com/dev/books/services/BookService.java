package com.dev.books.services;

import com.dev.books.domain.Book;

import java.util.Optional;

public interface BookService {

    Book create(Book book);

    Optional<Book> findById(String isbn);

}
