package ru.tnchk.library.services;

import ru.tnchk.library.model.Book;

import java.util.List;

public interface BookService {

    Book saveBook(String title, List<String> authorNames);

    List<Book> getBooksByAuthor(String author);

    List<Book> getAllBooks();
}
