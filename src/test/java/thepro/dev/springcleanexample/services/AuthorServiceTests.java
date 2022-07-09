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
public class AuthorServiceTests {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    @DisplayName("Test `addAuthor`")
    void testAddAuthor() {
        Author author = new Author("Malcolm", "Gladwell");

        authorService.addAuthor(author);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("Test `addBook` when Author is not present")
    void testAddBookWhenAuthorNotPresent() {
        Long authorId = 1L;
        Book book = new Book("Richest Man in Babylon");

        authorService.addBook(authorId, book);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(0)).save(book);
    }

    @Test
    @DisplayName("Test `addBook` when Author is present")
    void testAddBook() {
        Long authorId = 1L;
        Author author = new Author(authorId, "Malcolm", "Gladwell");
        Book book = new Book("Outlier: THe Story of Success");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.save(book)).thenReturn(book);
        authorService.addBook(authorId, book);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Test `deleteAuthor`")
    void testDeleteAuthor() {
        Long authorId = 1L;

        authorService.deleteAuthor(authorId);
        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    @DisplayName("Test `findAllAuthors`")
    void testFindAllAuthors() {
        authorService.findAllAuthors();
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test `findAuthor` when Author Id is present")
    void testFindAuthorById() {
        Long authorId = 1L;

        authorService.findAuthorById(authorId);
        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    @DisplayName("Test `findAuthor` when Author First Name is present")
    void testFindAuthorByFirstName() {
        String authorFirstName = "Malcolm";

        authorService.findAuthorByFirstName(authorFirstName);
        verify(authorRepository, times(1)).findByFirstName(authorFirstName);
    }

    @Test
    @DisplayName("Test `findAuthor` when Author Last Name is present")
    void testFindAuthorByLastName() {
        String authorLastName = "Gladwell";

        authorService.findAuthorByLastName(authorLastName);
        verify(authorRepository, times(1)).findByLastName(authorLastName);
    }

    @Test
    @DisplayName("Test `saveAuthor`")
    void testSaveAuthor() {
        Author author = new Author(1L, "Malcolm", "Gladwell");
        authorService.saveAuthor(author);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("Test `updateAuthor` when Author Id is not present")
    void testUpdateAuthorWhenAuthorIdInvalid() {
        Long authorId = 1L;
        Author author = new Author(authorId, "Malcolm", "Gladwell");

        when(authorRepository.existsById(authorId)).thenReturn(false);
        authorService.updateAuthor(authorId, author);
        verify(authorRepository, times(0)).save(author);
    }

    @Test
    @DisplayName("Test `updateAuthor` when Author Id is present")
    void testUpdateAuthor() {
        Long authorId = 1L;
        Author author = new Author(authorId, "Malcolm", "Gladwell");

        when(authorRepository.existsById(authorId)).thenReturn(true);
        when(authorRepository.save(author)).thenReturn(author);
        authorService.updateAuthor(authorId, author);
        verify(authorRepository, times(1)).save(author);
    }
}
