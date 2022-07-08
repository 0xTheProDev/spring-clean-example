package thepro.dev.springcleanexample.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thepro.dev.springcleanexample.entities.Author;
import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.repositories.AuthorRepository;
import thepro.dev.springcleanexample.repositories.BookRepository;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(@Autowired AuthorRepository authorRepository, @Autowired BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Book> addBook(Long id, Book book) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            return Optional.empty();
        }

        book.addAuthor(author.get());
        return Optional.of(this.bookRepository.save(book));
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> findAuthorByFirstName(String firstName) {
        return authorRepository.findByFirstName(firstName);
    }

    public Optional<Author> findAuthorByLastName(String lastName) {
        return authorRepository.findByLastName(lastName);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> updateAuthor(Long id, Author author) {
        if (!authorRepository.existsById(id)) {
            return Optional.empty();
        }

        author.setId(id);
        return Optional.of(authorRepository.save(author));
    }
}
