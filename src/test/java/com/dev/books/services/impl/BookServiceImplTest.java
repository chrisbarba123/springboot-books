package com.dev.books.services.impl;

import com.dev.books.domain.Book;
import com.dev.books.domain.BookEntity;
import com.dev.books.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        final Book book = Book.builder()
                .isbn("02345678")
                .author("Virginia Woolf")
                .title("The Waves")
                .build();

        final BookEntity bookEntity = BookEntity
                .builder()
                .isbn("02345678")
                .author("Virginia Woolf")
                .title("The Waves")
                .build();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.create(book);

        assertEquals(book, result);

    }

}
