package ru.tnchk.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.tnchk.library.model.Author;
import ru.tnchk.library.model.Book;
import ru.tnchk.library.services.AuthorService;
import ru.tnchk.library.services.BookServiceImpl;

import java.util.List;

@Controller

public class BookController {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private AuthorService authorService;

    @QueryMapping
    public List<Book> getBooksByAuthor(@Argument String author) {
        return bookService.getBooksByAuthor(author);
    }

    @QueryMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Author getAuthor(@Argument String name) {
        return authorService.getAuthor(name).orElse(null);
    }

    @MutationMapping
    public Book saveBook(@Argument String title, @Argument List<String> authors) {
        return bookService.saveBook(title, authors);
    }

    @MutationMapping
    public Author saveAuthor(@Argument String name, @Argument List<String> books) {
        return authorService.saveAuthor(name, books);
    }
}
