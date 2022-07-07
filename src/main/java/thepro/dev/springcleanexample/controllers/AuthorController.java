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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import thepro.dev.springcleanexample.dtos.AuthorDtos.AddAuthorDto;
import thepro.dev.springcleanexample.dtos.AuthorDtos.AuthorDto;
import thepro.dev.springcleanexample.dtos.AuthorDtos.UpdateAuthorDto;
import thepro.dev.springcleanexample.dtos.BookDtos.AddBookDto;
import thepro.dev.springcleanexample.dtos.BookDtos.BookDto;
import thepro.dev.springcleanexample.entities.Author;
import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.services.AuthorService;

@RestController
@RequestMapping(path = "/api/v1/authors", consumes = "application/json", produces = "application/json")
public class AuthorController {
    private AuthorService authorService;

    AuthorController(@Autowired AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody AuthorDto addAuthor(@RequestBody AddAuthorDto addAuthorDto) {
        Author author = new Author(addAuthorDto.getFirstName(), addAuthorDto.getLastName());
        return new AuthorDto(authorService.addAuthor(author));
    }

    @PostMapping(path = "/{id}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody BookDto addBook(@PathVariable("id") Long authorId, @RequestBody AddBookDto addBookDto) {
        Book book = new Book(addBookDto.getName());
        return authorService.addBook(authorId, book)
                .map(BookDto::new)
                .orElseThrow(this::throwNotFoundException);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }

    @GetMapping
    public @ResponseBody List<AuthorDto> getAllBooks() {
        return StreamSupport.stream(authorService.findAllAuthors().spliterator(), false)
                .map(AuthorDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.findAuthorById(id)
                .map(AuthorDto::new)
                .orElseThrow(this::throwNotFoundException);
    }

    @GetMapping(path = "/{id}/books")
    public @ResponseBody List<BookDto> getBooksById(@PathVariable("id") Long id) {
        return StreamSupport
                .stream(
                        authorService.findAuthorById(id)
                                .orElseThrow(this::throwNotFoundException)
                                .getBooks()
                                .spliterator(),
                        false)
                .map(BookDto::new).collect(Collectors.toList());
    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public @ResponseBody AuthorDto updateBook(@PathVariable("id") Long id,
            @RequestBody UpdateAuthorDto updateAuthorDto) {
        Author author = new Author(updateAuthorDto.getFirstName(), updateAuthorDto.getLastName());
        return authorService.updateAuthor(id, author).map(AuthorDto::new)
                .orElseThrow(this::throwNotFoundException);
    }

    @PutMapping()
    public @ResponseBody AuthorDto updateOrCreateBook(@RequestBody UpdateAuthorDto updateAuthorDto) {
        Author author = new Author(updateAuthorDto.getId().get(), updateAuthorDto.getFirstName(),
                updateAuthorDto.getLastName());
        return new AuthorDto(authorService.saveAuthor(author));
    }

    private ResponseStatusException throwNotFoundException() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
