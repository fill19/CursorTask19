package com.cursor.library.services;

import com.cursor.library.daos.BookDao;
import com.cursor.library.exceptions.BadIdException;
import com.cursor.library.exceptions.BookNameIsNullException;
import com.cursor.library.exceptions.BookNameIsTooLongException;
import com.cursor.library.models.Book;
import com.cursor.library.models.CreateBookDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)

public class BookServiceTest {

    private BookService bookService;
    private BookDao bookDao;

    @BeforeAll
    void setUp() {
        bookDao = Mockito.mock(BookDao.class);
        bookService = new BookService(bookDao);
    }

    @Test
    void getBookByIdSuccessTest() {
        String bookId = "book-id";

        when(bookDao.getById(bookId)).thenReturn(new Book(bookId));

        Book bookFromDB = bookService.getById(bookId);

        assertEquals(
                bookId,
                bookFromDB.getBookId()
        );
    }

    @Test
    void getBookByIdNullExceptionTest() {

        assertThrows(BadIdException.class, () -> bookService.getById(null));
    }

    @Test
    void getBookByIdBadIdExceptionTest() {
        assertThrows(
                BadIdException.class,
                () -> bookService.getById("       ")
        );
    }

    @Test
    void getValidatedBookNameExpectBookNameIsNullExceptionTest() {
        assertThrows(
                BookNameIsNullException.class,
                () -> bookService.getValidatedBookName(null)
        );
    }

    @Test
    void getValidateBookNameExpectBookNameIsTooLongExceptionTest() {
        assertThrows(
                BookNameIsTooLongException.class,
                () -> bookService.getValidatedBookName(" ".repeat(1001))
        );
    }

    @Test
    void createBookTest() {
        List<String> authors = Arrays.asList("Author1", "Author2", "Author3");
        CreateBookDto bookDto = CreateBookDto.builder()
                .name("BookDtoName")
                .description("BookDto description")
                .authors(authors)
                .yearOfPublication(2015)
                .numberOfWords(378)
                .rating(5)
                .build();

        Book book2 = Book.builder()
                .bookId("bookId")
                .name(bookDto.getName())
                .description(bookDto.getDescription())
                .authors(bookDto.getAuthors())
                .yearOfPublication(bookDto.getYearOfPublication())
                .numberOfWords(bookDto.getNumberOfWords())
                .rating(bookDto.getRating())
                .build();
        when(bookDao.addBook(any(Book.class))).thenReturn(book2);
        Book book1 = bookService.createBook(bookDto);

        assertEquals(book1, book2);
    }
}
