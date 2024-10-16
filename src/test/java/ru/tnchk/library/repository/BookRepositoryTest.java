package ru.tnchk.library.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.tnchk.library.AbstractIntegrationTest;
import ru.tnchk.library.model.Author;
import ru.tnchk.library.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({ "test" })
@SpringBootTest
@Transactional
public class BookRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindBooksByAuthorName() {
        // Создаем автора
        Author author1 = new Author();
        author1.setName("I.Turgenev");

        // Создаем книгу и добавляем автора
        Book book = new Book();
        book.setTitle("Fathers and sons");
        book.setAuthors(Set.of(author1));

        bookRepository.save(book);
        List<Book> books = bookRepository.findAll();
        Optional<Author> actualAuthor = books.get(0).getAuthors().stream().findFirst();


        // Проверяем, что книга найдена
        assertEquals(1, books.size());
        assertEquals("Fathers and sons", books.get(0).getTitle());
        assertEquals("I.Turgenev", actualAuthor.get().getName());
    }
}
