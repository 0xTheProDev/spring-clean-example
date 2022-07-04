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
@RequestMapping(path = "/books")
public class BookController {
    private BookService bookService;

    @Autowired
    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody BookDto addBook(@RequestBody AddBookDto addBookDto) {
        Book book = new Book(addBookDto.getName());
        return convertToDto(bookService.addBook(book));
    }

    @DeleteMapping(path = "{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public @ResponseBody List<BookDto> getAllBooks() {
        return StreamSupport.stream(bookService.findAllBooks().spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody BookDto getBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{id}")
    public @ResponseBody BookDto updateBook(@PathVariable("id") Long id, @RequestBody UpdateBookDto updateBookDto) {
        Book book = new Book(updateBookDto.getName());
        return bookService.updateBook(id, book).map(this::convertToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private BookDto convertToDto(Book book) {
        return new BookDto(book.getId(), book.getName());
    }

    // private Book convertToEntity(BookDto bookDto) {
    // Book book = new Book();
    // book.setId(bookDto.getId());
    // book.setName(bookDto.getName());
    // return book;
    // }
}
