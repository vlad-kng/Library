package ru.tnchk.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tnchk.library.model.Author;
import ru.tnchk.library.model.Book;
import ru.tnchk.library.repository.AuthorRepository;
import ru.tnchk.library.repository.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Author saveAuthor(String name, List<String> bookTitles) {
        Author author = new Author();
        author.setName(name);
        Set<Book> books = bookRepository.findBooksByTitleIn(bookTitles);
        author.setBooks(books);
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthor(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Set<Author> getOrCreateAuthors(List<String> authorNames) {
        Set<Author> existingAuthors = new HashSet<>(authorRepository.findAllByNameIn(authorNames));

        // Создаем новых авторов и добавляем их в существующих, если они не были найдены
        authorNames.stream()
                .filter(authorName -> existingAuthors.stream().noneMatch(author -> author.getName().equals(authorName)))
                .map(authorName -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    return newAuthor;
                })
                .forEach(existingAuthors::add);

        return existingAuthors;
    }
}

