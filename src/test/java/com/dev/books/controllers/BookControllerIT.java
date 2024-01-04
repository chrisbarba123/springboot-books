package com.dev.books.controllers;

import com.dev.books.TestData;
import com.dev.books.domain.Book;
import com.dev.books.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testThatBookIsCreated() throws Exception {
        final Book book = TestData.testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + book.getIsbn()).contentType("application/json").content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testThatRetrieveBookReturns404WhenBookNotFound() throws Exception {
        final Book book = TestData.testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/123123123"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatRetrieveBookReturnsHttp200WhenExists() throws Exception {
        final Book book = TestData.testBook();
        bookService.create(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));

    }

    @Test
    public void testThatRetrieveAllBooksReturnsHttp200WhenExists() throws Exception {
        final Book book = TestData.testBook();

        bookService.create(book);


        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author").value(book.getAuthor()));


    }
}
