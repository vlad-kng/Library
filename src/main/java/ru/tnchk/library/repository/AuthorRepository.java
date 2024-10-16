package ru.tnchk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tnchk.library.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    Set<Author> findAllByNameIn(List<String> authorNames);
}
