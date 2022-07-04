package thepro.dev.springcleanexample.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.repositories.BookRepository;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
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
