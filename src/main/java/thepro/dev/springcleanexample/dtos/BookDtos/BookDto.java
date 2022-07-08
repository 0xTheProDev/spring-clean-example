package thepro.dev.springcleanexample.dtos.BookDtos;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import thepro.dev.springcleanexample.dtos.AuthorDtos.*;
import thepro.dev.springcleanexample.entities.Book;

public class BookDto {
    private Long id;
    private String name;
    private Set<AuthorDto> authors = new HashSet<AuthorDto>();

    public BookDto() {
    }

    public BookDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BookDto(Long id, String name, Set<AuthorDto> authors) {
        this.id = id;
        this.name = name;
        this.authors = authors;
    }

    public BookDto(Book book) {
        id = book.getId();
        name = book.getName();
        authors = book.getAuthors().stream().map(AuthorDto::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AuthorDto> getAuthors() {
        return authors;
    }

    public void addAuthor(AuthorDto author) {
        authors.add(author);
    }

    public void removeAuthor(AuthorDto author) {
        authors.removeIf((prevAuthor) -> prevAuthor.getId().equals(author.getId()));
    }

    public void resetAuthors() {
        authors.clear();
    }

    public void setAuthors(Set<AuthorDto> authors) {
        this.authors = authors;
    }
}
