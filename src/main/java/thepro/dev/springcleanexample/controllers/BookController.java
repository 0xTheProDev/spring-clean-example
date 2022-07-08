package thepro.dev.springcleanexample.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import thepro.dev.springcleanexample.dtos.BookDtos.AddBookDto;
import thepro.dev.springcleanexample.dtos.BookDtos.BookDto;
import thepro.dev.springcleanexample.dtos.BookDtos.UpdateBookDto;
import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.services.BookService;

@RestController
@RequestMapping(path = "/api/v1/books", consumes = "application/json", produces = "application/json")
public class BookController {
    private BookService bookService;

    BookController(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody BookDto addBook(@RequestBody AddBookDto addBookDto) {
        Book book = new Book(addBookDto.getName());
        return new BookDto(bookService.addBook(book));
    }

    @PostMapping(path = "/{id}/authors/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody BookDto addAuthor(@PathVariable("id") Long bookId, @PathVariable("authorId") Long authorId) {
        return bookService.addAuthorById(bookId, authorId)
                .map(BookDto::new)
                .orElseThrow(this::throwNotFoundException);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public @ResponseBody List<BookDto> getAllBooks() {
        return StreamSupport.stream(bookService.findAllBooks().spliterator(), false)
                .map(BookDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody BookDto getBookById(@PathVariable("id") Long id) {
        return bookService.findBook(id)
                .map(BookDto::new)
                .orElseThrow(this::throwNotFoundException);
    }

    @DeleteMapping(path = "/{id}/authors/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAuthor(@PathVariable("id") Long bookId, @PathVariable("authorId") Long authorId) {
        bookService.removeAuthorById(bookId, authorId);
    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public @ResponseBody BookDto updateBook(@PathVariable("id") Long id, @RequestBody UpdateBookDto updateBookDto) {
        Book book = new Book(updateBookDto.getName());
        return bookService.updateBook(id, book).map(BookDto::new)
                .orElseThrow(this::throwNotFoundException);
    }

    @PutMapping()
    public @ResponseBody BookDto updateOrCreateBook(@RequestBody UpdateBookDto updateBookDto) {
        Book book = new Book(updateBookDto.getId().get(), updateBookDto.getName());
        return new BookDto(bookService.saveBook(book));
    }

    private ResponseStatusException throwNotFoundException() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
