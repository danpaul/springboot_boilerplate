package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.enums.BookFormats;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createBook_persistsAndReturnsCreatedBook() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("name", "Functional Testing in Spring");
        request.put("author", "Integration Author");
        request.put("isbn", "9781234500011");
        request.put("format", "Hardcover");
        request.put("isBorrowed", false);
        request.put("isReference", false);
        request.put("isPremium", false);
        request.put("publishedDate", "2008-08-01");

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Functional Testing in Spring"))
                .andExpect(jsonPath("$.author").value("Integration Author"))
                .andExpect(jsonPath("$.isbn").value("9781234500011"))
                .andExpect(jsonPath("$.format").value("Hardcover"))
                .andExpect(jsonPath("$.borrowed").value(false))
                .andExpect(jsonPath("$.reference").value(false))
                .andExpect(jsonPath("$.premium").value(false));
    }

    @Test
    void getBookById_returnsBookFromDatabase() throws Exception {
        Book book = new Book();
        book.setName("Book Lookup");
        book.setAuthor("Query Author");
        book.setIsbn("9781234500012");
        book.setFormat(BookFormats.Paperback);
        Book savedBook = bookRepository.save(book);

        mockMvc.perform(get("/books/{id}", savedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedBook.getId()))
                .andExpect(jsonPath("$.name").value("Book Lookup"))
                .andExpect(jsonPath("$.author").value("Query Author"))
                .andExpect(jsonPath("$.isbn").value("9781234500012"))
                .andExpect(jsonPath("$.format").value("Paperback"))
                .andExpect(jsonPath("$.borrowed").value(false))
                .andExpect(jsonPath("$.reference").value(false))
                .andExpect(jsonPath("$.premium").value(false));
    }

    @Test
    void getBookById_whenMissing_returnsNotFound() throws Exception {
        mockMvc.perform(get("/books/{id}", 999999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found"));
    }

    @Test
    void getAll_withSearchTerm_filtersByName() throws Exception {
        Book match = new Book();
        match.setName("Spring in Action");
        match.setAuthor("Search Author");
        match.setIsbn("9781234500091");
        match.setFormat(BookFormats.Paperback);
        bookRepository.save(match);

        Book nonMatch = new Book();
        nonMatch.setName("Hibernate Recipes");
        nonMatch.setAuthor("Other Author");
        nonMatch.setIsbn("9781234500092");
        nonMatch.setFormat(BookFormats.Hardcover);
        bookRepository.save(nonMatch);

        mockMvc.perform(get("/books").queryParam("term", "spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name").value(hasItem("Spring in Action")));
    }

    @Test
    void borrowBook_forMemberAndAvailableBook_marksBookAsBorrowedAndLinksUser() throws Exception {
        User user = new User();
        user.setUsername("functional-member");
        user.setEmail("functional-member@example.com");
        user.setMember(true);
        user.setPremiumMember(false);
        user.setBorrowedBooks(new ArrayList<>());
        User savedUser = userRepository.save(user);

        Book book = new Book();
        book.setName("Borrowable Book");
        book.setAuthor("Borrow Author");
        book.setIsbn("9781234500013");
        book.setFormat(BookFormats.EBook);
        book.setPremium(false);
        book.setReference(false);
        book.setBorrowed(false);
        Book savedBook = bookRepository.save(book);

        Map<String, Object> request = new HashMap<>();
        request.put("userId", savedUser.getId());

        mockMvc.perform(patch("/books/{id}", savedBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedBook.getId()))
                .andExpect(jsonPath("$.borrowed").value(true));

        User reloadedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertTrue(reloadedUser.getBorrowedBooks().stream().anyMatch(b -> b.getId() == savedBook.getId()));
    }

    @Test
    void borrowBook_forNonMember_returnsBadRequest() throws Exception {
        User user = new User();
        user.setUsername("functional-non-member");
        user.setEmail("functional-non-member@example.com");
        user.setMember(false);
        user.setPremiumMember(false);
        user.setBorrowedBooks(new ArrayList<>());
        User savedUser = userRepository.save(user);

        Book book = new Book();
        book.setName("Non Member Blocked");
        book.setAuthor("Policy Author");
        book.setIsbn("9781234500014");
        book.setFormat(BookFormats.Audiobook);
        book.setPremium(false);
        book.setReference(false);
        book.setBorrowed(false);
        Book savedBook = bookRepository.save(book);

        Map<String, Object> request = new HashMap<>();
        request.put("userId", savedUser.getId());

        // TODO:
        mockMvc.perform(patch("/books/{id}", savedBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Only members can borrow books"));
    }
}
