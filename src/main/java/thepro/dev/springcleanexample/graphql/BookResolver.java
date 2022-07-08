package thepro.dev.springcleanexample.graphql;

import org.springframework.stereotype.Controller;

import thepro.dev.springcleanexample.entities.Book;
import thepro.dev.springcleanexample.services.BookService;

@Controller
public class BookResolver {
    private final BookService bookService;

    BookResolver(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public Book getBook(@Argument Long id) {
        return bookService.findBookById(id);
    }
}
