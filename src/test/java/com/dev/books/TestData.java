package com.dev.books;

import com.dev.books.domain.Book;
import com.dev.books.domain.BookEntity;
import java.util.List;

public final class TestData {

    public TestData(){

    }

    public static Book testBook() {
        return Book
                .builder()
                .isbn("02345678")
                .author("Virginia Woolf")
                .title("The Waves")
                .build();
    }


    public static BookEntity testBookEntity() {
        return BookEntity
                .builder()
                .isbn("02345678")
                .author("Virginia Woolf")
                .title("The Waves")
                .build();
    }
}
