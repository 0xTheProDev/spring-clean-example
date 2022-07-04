package thepro.dev.springcleanexample.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import thepro.dev.springcleanexample.entities.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Optional<Author> findByFirstName(String firstName);

    Optional<Author> findByLastName(String lastName);
}
