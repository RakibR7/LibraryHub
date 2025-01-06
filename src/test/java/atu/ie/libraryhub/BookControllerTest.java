package atu.ie.libraryhub;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void testGetAllBooks() throws Exception {
        Book book1 = new Book(2L, "TitleA", "AuthorA", "GenreA", true);
        Book book2 = new Book(3L, "TitleB", "AuthorB", "GenreB", false);

        when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("TitleA"))
                .andExpect(jsonPath("$[1].availability").value(false));
    }

    @Test
    void testGetBookById_found() throws Exception {
        Book book = new Book(2L, "TitleA", "AuthorA", "GenreA", true);
        when(bookService.getBookById(2L)).thenReturn(book);

        mockMvc.perform(get("/api/books/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("AuthorA"));
    }

    @Test
    void testGetBookById_notFound() throws Exception {
        when(bookService.getBookById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddBook() throws Exception {
        Book newBook = new Book(null, "TitleX", "AuthorX", "GenreX", true);
        Book savedBook = new Book(7L, "TitleX", "AuthorX", "GenreX", true);

        when(bookService.addBook(Mockito.any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "title": "TitleX",
                      "author": "AuthorX",
                      "genre": "GenreX",
                      "availability": true
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7));
    }

    @Test
    void testBorrowBook() throws Exception {
        when(bookService.borrowBook(2L)).thenReturn(true);

        mockMvc.perform(post("/api/books/2/borrow"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testReturnBook() throws Exception {
        mockMvc.perform(post("/api/books/2/return"))
                .andExpect(status().isOk());
        // If you want, you can also 'when(bookService.returnBook(2L))' for more logic
    }
}
