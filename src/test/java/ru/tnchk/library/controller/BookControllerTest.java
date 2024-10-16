package ru.tnchk.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.tnchk.library.AbstractIntegrationTest;
import ru.tnchk.library.graphql.GraphQLRequest;
import ru.tnchk.library.model.Author;
import ru.tnchk.library.model.Book;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({ "test" })
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveBook() throws Exception {

        Author author = new Author();
        author.setName("A.Pushkin");

        Book book = new Book();
        book.setTitle("Evgeniy Onegin");
        book.setAuthors(Set.of(author));


        String mutationSaveBook = String.format("""
                mutation {
                    saveBook(title: "%s", authors: ["%s"]) {
                        title
                        authors {
                            name
                        }
                    }
                }
                """, book.getTitle(), author.getName());


        mockMvc.perform(MockMvcRequestBuilders.post("/graphql")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new GraphQLRequest(mutationSaveBook))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.saveBook.title").value(book.getTitle()))
                .andExpect(jsonPath("$.data.saveBook.authors[0].name").value(author.getName()));
    }
    @Test
    void testFindBooksByAuthorName() throws Exception {
        Author author = new Author();
        author.setName("A.Pushkin");


        Book book = new Book();
        book.setTitle("Evgeniy Onegin");
        book.setAuthors(Set.of(author));


        String mutationSaveBook = String.format("""
                mutation {
                    saveBook(title: "%s", authors: ["%s"]) {
                        title
                    }
                }
                """, book.getTitle(), author.getName());

        mockMvc.perform(MockMvcRequestBuilders.post("/graphql")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new GraphQLRequest(mutationSaveBook))))
                .andExpect(status().isOk());


        String queryFindBooksByAuthor = """
                query {
                    getBooksByAuthor(author: "%s") {
                        title
                    }
                }
                """.formatted(author.getName());

        mockMvc.perform(MockMvcRequestBuilders.post("/graphql")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new GraphQLRequest(queryFindBooksByAuthor))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.getBooksByAuthor[0].title").value(book.getTitle()));
    }
}
