package thepro.dev.springcleanexample.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import thepro.dev.springcleanexample.entities.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByName(String name);
}
