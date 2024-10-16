package ru.tnchk.library.services;

import ru.tnchk.library.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorService {

    Author saveAuthor(String name, List<String> bookTitles);

    Optional<Author> getAuthor(String name);

    Set<Author> getOrCreateAuthors(List<String> authorNames);
}
