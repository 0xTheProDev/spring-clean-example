package thepro.dev.springcleanexample.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import thepro.dev.springcleanexample.dtos.AuthorDtos.AddAuthorDto;
import thepro.dev.springcleanexample.dtos.AuthorDtos.AuthorDto;
import thepro.dev.springcleanexample.dtos.AuthorDtos.UpdateAuthorDto;
import thepro.dev.springcleanexample.dtos.BookDtos.AddBookDto;
import thepro.dev.springcleanexample.dtos.BookDtos.BookDto;
import thepro.dev.springcleanexample.entities.Author;
import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.services.AuthorService;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTests {
    @Mock
    AuthorService authorService;

    @InjectMocks
    AuthorController authorController;

    @Test
    @DisplayName("Test `addAuthor`")
    void testAddAuthor() {
        AddAuthorDto addAuthorDto = new AddAuthorDto("Malcolm", "Gladwell");
        Author author = new Author(1L, addAuthorDto.getFirstName(), addAuthorDto.getLastName());

        when(authorService.addAuthor(any(Author.class))).thenReturn(author);
        AuthorDto authorDto = authorController.addAuthor(addAuthorDto);
        verify(authorService, times(1)).addAuthor(any(Author.class));
        assertEquals(authorDto.getId(), author.getId());
    }

    @Test
    @DisplayName("Test `addBook` when Author Id is Invalid")
    void testAddBookWhenAuthorNotPresent() {
        Long authorId = 1L;
        AddBookDto addBookDto = new AddBookDto("Richest Man in Babylon");

        when(authorService.addBook(anyLong(), any())).thenReturn(Optional.empty());
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                authorController.addBook(authorId, addBookDto);
            }
        };
        assertThrows(ResponseStatusException.class, executable);
        verify(authorService, times(1)).addBook(eq(authorId), any(Book.class));
    }

    @Test
    @DisplayName("Test `addBook` when Author Id is Valid")
    void testAddBookWhenAuthorPresent() {
        Long authorId = 1L, bookId = 2L;
        AddBookDto addBookDto = new AddBookDto("Richest Man in Babylon");

        when(authorService.addBook(anyLong(), any())).thenReturn(Optional.of(new Book(bookId)));
        BookDto bookDto = authorController.addBook(authorId, addBookDto);
        assertEquals(bookId, bookDto.getId());
        verify(authorService, times(1)).addBook(eq(authorId), any(Book.class));
    }

    @Test
    @DisplayName("Test `deleteAuthor`")
    void testDeleteAuthor() {
        Long authorId = 1L;

        authorController.deleteAuthor(authorId);
        verify(authorService, times(1)).deleteAuthor(authorId);
    }

    @Test
    @DisplayName("Test `getAllAuthors`")
    void testGetAllAuthors() {
        authorController.getAllAuthors();
        verify(authorService, times(1)).findAllAuthors();
    }

    @Test
    @DisplayName("Test `getAuthorById` when Author Id is Invalid")
    void testGetAuthorByIdWhenInvalidId() {
        Long authorId = 1L;

        when(authorService.findAuthorById(authorId)).thenReturn(Optional.empty());
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                authorController.getAuthorById(authorId);
            }
        };
        assertThrows(ResponseStatusException.class, executable);
        verify(authorService, times(1)).findAuthorById(authorId);
    }

    @Test
    @DisplayName("Test `getAuthorById` when Author Id is Valid")
    void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author(authorId);

        when(authorService.findAuthorById(authorId)).thenReturn(Optional.of(author));
        AuthorDto authorDto = authorController.getAuthorById(authorId);
        assertEquals(authorId, authorDto.getId());
        verify(authorService, times(1)).findAuthorById(authorId);
    }

    @Test
    @DisplayName("Test `getBooksById` when Author Id is Invalid")
    void testGetBooksByIdWhenAuthorNotPresent() {
        Long authorId = 1L;

        when(authorService.findAuthorById(authorId)).thenReturn(Optional.empty());
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                authorController.getBooksById(authorId);
            }
        };
        assertThrows(ResponseStatusException.class, executable);
        verify(authorService, times(1)).findAuthorById(authorId);
    }

    @Test
    @DisplayName("Test `getBooksById` when Author Id is Valid")
    void testGetBooksByIdWhenAuthorPresent() {
        Long authorId = 1L, bookId = 2L;
        Author author = new Author(authorId);
        Book book = new Book(bookId, "The Richest Man in Babylon");
        author.addBook(book);

        when(authorService.findAuthorById(authorId)).thenReturn(Optional.of(author));
        List<BookDto> bookDto = authorController.getBooksById(authorId);
        assertEquals(bookId, bookDto.get(0).getId());
        verify(authorService, times(1)).findAuthorById(authorId);
    }

    @Test
    @DisplayName("Test `updateAuthor` when Author Id is Invalid")
    void testUpdateAuthorWhenAuthorNotPresent() {
        Long authorId = 1L;
        UpdateAuthorDto updateAuthorDto = new UpdateAuthorDto("Malcolm", "Gladwell");

        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(Optional.empty());
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                authorController.updateAuthor(authorId, updateAuthorDto);
            }
        };
        assertThrows(ResponseStatusException.class, executable);
        verify(authorService, times(1)).updateAuthor(eq(authorId), any(Author.class));
    }

    @Test
    @DisplayName("Test `updateAuthor` when Author Id is Valid")
    void testUpdateAuthorWhenAuthorPresent() {
        Long authorId = 1L;
        UpdateAuthorDto updateAuthorDto = new UpdateAuthorDto("Malcolm", "Gladwell");
        Author author = new Author(authorId, updateAuthorDto.getFirstName(), updateAuthorDto.getLastName());

        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(Optional.of(author));
        AuthorDto authorDto = authorController.updateAuthor(authorId, updateAuthorDto);
        assertEquals(authorId, authorDto.getId());
        verify(authorService, times(1)).updateAuthor(eq(authorId), any(Author.class));
    }

    @Test
    @DisplayName("Test `updateOrCreateAuthor`")
    void testUpdateOrCreateAuthor() {
        Long authorId = 1L;
        UpdateAuthorDto updateAuthorDto = new UpdateAuthorDto(authorId, "Malcolm", "Gladwell");
        Author author = new Author(authorId, updateAuthorDto.getFirstName(), updateAuthorDto.getLastName());

        when(authorService.saveAuthor(any(Author.class))).thenReturn(author);
        AuthorDto authorDto = authorController.updateOrCreateAuthor(updateAuthorDto);
        verify(authorService, times(1)).saveAuthor(any(Author.class));
        assertEquals(authorDto.getId(), author.getId());
    }
}
