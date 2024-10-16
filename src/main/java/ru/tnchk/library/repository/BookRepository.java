package ru.tnchk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tnchk.library.model.Book;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT b.* FROM book b " +
            "JOIN author_books ab ON b.id = ab.book_id " +
            "JOIN author a ON a.id = ab.author_id " +
            "WHERE a.name = :authorName", nativeQuery = true)
    List<Book> findBooksByAuthorsName(@Param("authorName") String authorName);
    Set<Book> findBooksByTitleIn(List<String> titles);
}
