package thepro.dev.springcleanexample.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thepro.dev.springcleanexample.entities.Author;
import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.repositories.AuthorRepository;
import thepro.dev.springcleanexample.repositories.BookRepository;

@Service
public class BookService {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    BookService(@Autowired AuthorRepository authorRepository, @Autowired BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> addAuthorById(Long bookId, Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);

        if (author.isEmpty()) {
            return Optional.empty();
        }

        return bookRepository.findById(bookId).map(((book) -> {
            book.addAuthor(author.get());
            return bookRepository.save(book);
        }));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteBook(Book book) {
        bookRepository.deleteById(book.getId());
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> findBookByName(String name) {
        return bookRepository.findByName(name);
    }

    public Optional<Book> removeAuthorById(Long bookId, Long authorId) {
        return bookRepository.findById(bookId).map((book) -> {
            Author author = new Author(authorId);
            book.removeAuthor(author);
            return bookRepository.save(book);
        });
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            return Optional.empty();
        }

        book.setId(id);
        return Optional.of(bookRepository.save(book));
    }
}
