package thepro.dev.springcleanexample.repositories;

import org.springframework.data.repository.CrudRepository;

import thepro.dev.springcleanexample.entities.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
