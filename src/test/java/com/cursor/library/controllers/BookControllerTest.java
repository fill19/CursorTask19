package com.cursor.library.controllers;

import com.cursor.library.daos.BookDao;
import com.cursor.library.models.Book;
import com.cursor.library.models.CreateBookDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

public class BookControllerTest extends BaseControllerTest {

    BookDao bookDao;


    @BeforeAll
    void setUp() {
        bookDao = new BookDao();

    }

    @Test
    void getAllSuccessTest() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books");
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        List<Book> bookListTest = OBJECT_MAPPER.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        List<Book> booklist = bookDao.getAll();
        Assertions.assertEquals(bookListTest, booklist);
    }

    @Test
    void getByIdSuccessTest() throws Exception {
        Book book = bookDao.getById("random_id_value_3");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/{bookId}", book.getBookId());
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    }

    @Test
    void getByIdNotFoundTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/{bookId}", "bookId");
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void DeleteByIdSuccessTest() throws Exception {
        Book book = bookDao.getById("random_id_value_1");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/{bookId}", book.getBookId());
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
        bookDao.deleteById("random_id_value_1");

    }

    @Test
    void deleteByIdNotFoundTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/{bookId}", "bookId");
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createBookTest() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("Cool createBookDto");
        createBookDto.setDescription("Cool description");
        createBookDto.setNumberOfWords(100500);
        createBookDto.setRating(10);
        createBookDto.setYearOfPublication(2020);
        createBookDto.setAuthors(Arrays.asList("author1", "author2"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(createBookDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Book book = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                Book.class
        );
        bookDao.addBook(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + book.getBookId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
