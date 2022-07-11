package thepro.dev.springcleanexample.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import thepro.dev.springcleanexample.dtos.BookDtos.AddBookDto;
import thepro.dev.springcleanexample.dtos.BookDtos.BookDto;
import thepro.dev.springcleanexample.dtos.BookDtos.UpdateBookDto;
import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.services.BookService;

@ExtendWith(MockitoExtension.class)
public class BookControllerTests {
    @Mock
    BookService bookService;

    @InjectMocks
    BookController bookController;

    @Test
    @DisplayName("Test `addBook`")
    void testAddBook() {
        AddBookDto addBookDto = new AddBookDto("The Richest Man in Babylon");

        when(bookService.addBook(any(Book.class))).thenReturn(new Book(addBookDto.getName()));
        BookDto bookDto = bookController.addBook(addBookDto);
        verify(bookService, times(1)).addBook(any(Book.class));
        assertEquals(addBookDto.getName(), bookDto.getName());
    }

    @Test
    @DisplayName("Test `addAuthor` when Book Id is Invalid")
    void testAddAuthorWhenBookNotPresent() {
        Long authorId = 1L, bookId = 2L;

        when(bookService.addAuthorById(bookId, authorId)).thenReturn(Optional.empty());
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                bookController.addAuthor(bookId, authorId);
            }
        };
        assertThrows(ResponseStatusException.class, executable);
        verify(bookService, times(1)).addAuthorById(bookId, authorId);
    }

    @Test
    @DisplayName("Test `addAuthor` when Book Id is Valid")
    void testAddAuthorWhenBookPresent() {
        Long authorId = 1L, bookId = 2L;
        Book book = new Book(bookId);

        when(bookService.addAuthorById(bookId, authorId)).thenReturn(Optional.of(book));
        BookDto bookDto = bookController.addAuthor(bookId, authorId);
        assertEquals(book.getId(), bookDto.getId());
        verify(bookService, times(1)).addAuthorById(bookId, authorId);
    }

    @Test
    @DisplayName("Test `deleteBook`")
    void testDeleteBook() {
        Long bookId = 1L;

        bookController.deleteBook(bookId);
        verify(bookService, times(1)).deleteBook(bookId);
    }

    @Test
    @DisplayName("Test `getAllBooks`")
    void testGetAllBooks() {
        bookController.getAllBooks();
        verify(bookService, times(1)).findAllBooks();
    }

    @Test
    @DisplayName("Test `getBookById` when Book Id is Invalid")
    void testGetBookByIdWhenInvalidId() {
        Long bookId = 1L;

        when(bookService.findBook(bookId)).thenReturn(Optional.empty());
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                bookController.getBookById(bookId);
            }
        };
        assertThrows(ResponseStatusException.class, executable);
        verify(bookService, times(1)).findBook(bookId);
    }

    @Test
    @DisplayName("Test `getBookById` when Book Id is Valid")
    void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book(bookId);

        when(bookService.findBook(bookId)).thenReturn(Optional.of(book));
        BookDto bookDto = bookController.getBookById(bookId);
        assertEquals(bookId, bookDto.getId());
        verify(bookService, times(1)).findBook(bookId);
    }

    @Test
    @DisplayName("Test `removeAuthor`")
    void testRemoveAuthor() {
        Long authorId = 1L, bookId = 2L;

        bookController.removeAuthor(bookId, authorId);
        verify(bookService, times(1)).removeAuthorById(bookId, authorId);
    }

    @Test
    @DisplayName("Test `updateBook` when Book Id is Invalid")
    void testUpdateBookWhenBookNotPresent() {
        Long bookId = 1L;
        UpdateBookDto updateBookDto = new UpdateBookDto(bookId, "The Richest Man in Babylon");

        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(Optional.empty());
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                bookController.updateBook(bookId, updateBookDto);
            }
        };
        assertThrows(ResponseStatusException.class, executable);
        verify(bookService, times(1)).updateBook(eq(bookId), any(Book.class));
    }

    @Test
    @DisplayName("Test `updateBook` when Book Id is Valid")
    void testUpdateBookWhenBookPresent() {
        Long bookId = 1L;
        UpdateBookDto updateBookDto = new UpdateBookDto(bookId, "The Richest Man in Babylon");
        Book book = new Book(bookId, updateBookDto.getName());

        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(Optional.of(book));
        BookDto bookDto = bookController.updateBook(bookId, updateBookDto);
        assertEquals(bookId, bookDto.getId());
        verify(bookService, times(1)).updateBook(eq(bookId), any(Book.class));
    }

    @Test
    @DisplayName("Test `updateOrCreateBook`")
    void testUpdateOrCreateBook() {
        Long bookId = 1L;
        UpdateBookDto updateBookDto = new UpdateBookDto(bookId, "The Richest Man in Babylon");
        Book book = new Book(bookId, updateBookDto.getName());

        when(bookService.saveBook(any(Book.class))).thenReturn(book);
        BookDto bookDto = bookController.updateOrCreateBook(updateBookDto);
        verify(bookService, times(1)).saveBook(any(Book.class));
        assertEquals(bookDto.getId(), book.getId());
    }
}
