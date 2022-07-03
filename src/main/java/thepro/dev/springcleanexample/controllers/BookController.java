package thepro.dev.springcleanexample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.repositories.BookRepository;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @PostMapping(path = "")
    public @ResponseBody Book addBook(@RequestParam String name) {
        Book n = new Book();
        n.setName(name);
        return bookRepository.save(n);
    }

    @GetMapping(path = "")
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
