package com.dev.books.services.impl;

import com.dev.books.domain.Book;
import com.dev.books.domain.BookEntity;
import com.dev.books.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.dev.books.TestData.*;

import java.util.Optional;

import static com.dev.books.TestData.testBook;
import static com.dev.books.TestData.testBookEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {
        final Book book = testBook();

        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.create(book);

        assertEquals(book, result);

    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook() {
        final String isbn = "12515021";

        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());

        final Optional<Book> result = underTest.findById(isbn);

        assertEquals(Optional.empty(), result);

    }

    @Test public void testThatFindByIdReturnsBookWithCorrectId() {
        final BookEntity bookEntity = testBookEntity();
        final Book book = testBook();
        final String isbn = book.getIsbn();


        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = underTest.findById(isbn);
        assertEquals( Optional.of(book),result);
    }

}
