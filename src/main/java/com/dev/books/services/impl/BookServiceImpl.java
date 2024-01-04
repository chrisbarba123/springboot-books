package com.dev.books.services.impl;

import com.dev.books.domain.Book;
import com.dev.books.domain.BookEntity;
import com.dev.books.repositories.BookRepository;
import com.dev.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);

        return bookEntityToBook(savedBookEntity);

    }

    @Override
    public Optional<Book> findById(final String isbn) {
        final Optional<BookEntity> foundBookEntity = bookRepository.findById(isbn);

        return foundBookEntity.map(book -> bookEntityToBook(book));

    }

    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }

    private Book bookEntityToBook(BookEntity book) {
        return Book.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
