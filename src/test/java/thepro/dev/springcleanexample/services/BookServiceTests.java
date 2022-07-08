package thepro.dev.springcleanexample.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import thepro.dev.springcleanexample.entities.Author;
import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.repositories.AuthorRepository;
import thepro.dev.springcleanexample.repositories.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Test `addBook`")
    void testAddBook() {
        Book book = new Book("Richest Man in Babylon");
        bookService.addBook(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Test `addAuthorById` when Author is not present")
    void testAddAuthorByIdWhenAuthorIdInvalid() {
        Long bookId = 1L, authorId = 2L;

        when(authorRepository.findById(2L)).thenReturn(Optional.empty());
        bookService.addAuthorById(bookId, authorId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(0)).findById(bookId);
    }

    @Test
    @DisplayName("Test `addAuthorById` when Book is not present")
    void testAddAuthorByIdWhenBookIdInvalid() {
        Long bookId = 1L, authorId = 2L;

        when(authorRepository.findById(2L)).thenReturn(Optional.of(new Author("Malcolm", "Gladwell")));
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        bookService.addAuthorById(bookId, authorId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Test `addAuthorById` when both Author and Book are present")
    void testAddAuthorById() {
        Long authorId = 2L, bookId = 1L;
        Book book = new Book(bookId, "Outliers: The Story of Success");

        when(authorRepository.findById(2L)).thenReturn(Optional.of(new Author("Malcolm", "Gladwell")));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.addAuthorById(bookId, authorId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Test `deleteBook` when Book Id is present")
    void testDeleteBookById() {
        Long bookId = 1L;

        bookService.deleteBook(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Test `deleteBook` when Book is present")
    void testDeleteBook() {
        Long bookId = 1L;
        Book book = new Book(bookId);

        bookService.deleteBook(book);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Test `findAllBooks`")
    void testFindAllBooks() {
        bookService.findAllBooks();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test `findBook` when Book Id is present")
    void testFindBookById() {
        Long bookId = 1L;

        bookService.findBook(bookId);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Test `findBook` when Book Name is present")
    void testFindBookByName() {
        String bookName = "Richest Man in Babylon";

        bookService.findBook(bookName);
        verify(bookRepository, times(1)).findByName(bookName);
    }

    @Test
    @DisplayName("Test `removeAuthorById` when Book Id is not present")
    void testRemoveAuthorByIdWhenBookIdInvalid() {
        Long bookId = 1L, authorId = 2L;

        bookService.removeAuthorById(bookId, authorId);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Test `removeAuthorById` when Book Id is present")
    void testRemoveAuthorById() {
        Long bookId = 1L, authorId = 2L;
        Book book = new Book(bookId, "Richest Man in Babylon");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        bookService.removeAuthorById(bookId, authorId);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Test `saveBook`")
    void testSaveBook() {
        Book book = new Book(1L, "Richest Man in Babylon");
        bookService.saveBook(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Test `updateBook` when Book Id is not present")
    void testUpdateBookWhenBookIdInvalid() {
        Long bookId = 1L;
        Book book = new Book("Richest Man in Babylon");

        when(bookRepository.existsById(bookId)).thenReturn(false);
        bookService.updateBook(bookId, book);
        verify(bookRepository, times(0)).save(book);
    }

    @Test
    @DisplayName("Test `updateBook` when Book Id is present")
    void testUpdateBook() {
        Long bookId = 1L;
        Book book = new Book("Richest Man in Babylon");

        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookRepository.save(book)).thenReturn(book);
        bookService.updateBook(bookId, book);
        verify(bookRepository, times(1)).save(book);
    }
}
