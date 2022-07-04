package thepro.dev.springcleanexample.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thepro.dev.springcleanexample.entities.Author;
import thepro.dev.springcleanexample.repositories.AuthorRepository;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
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

    public Author updateAuthor(Long id, Author author) {
        author.setId(id);
        return authorRepository.save(author);
    }
}
