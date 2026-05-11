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


}
