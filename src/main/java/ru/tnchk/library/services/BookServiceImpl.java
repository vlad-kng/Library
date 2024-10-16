package ru.tnchk.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tnchk.library.model.Author;
import ru.tnchk.library.model.Book;
import ru.tnchk.library.repository.AuthorRepository;
import ru.tnchk.library.repository.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public Book saveBook(String title, List<String> authorNames) {
        Book book = new Book();
        book.setTitle(title);

        // Получаем и создаем авторов
        Set<Author> authors = authorService.getOrCreateAuthors(authorNames);
        book.setAuthors(authors);


        authors.forEach(author -> author.getBooks().add(book));

        return bookRepository.save(book);
    }



    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthorsName(author);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
