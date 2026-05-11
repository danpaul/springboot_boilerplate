package com.example.demo.entity;

import com.example.demo.enums.BookFormats;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class BookEntityIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void saveAndLoad_persistsBookFieldsIncludingFlags() {
        Book book = new Book();
        book.setName("Integration Testing with Spring");
        book.setAuthor("Jane Doe");
        book.setIsbn("9781234567890");
        book.setFormat(BookFormats.Hardcover);
        book.setBorrowed(true);
        book.setReference(false);
        book.setPremium(true);

        Book saved = bookRepository.save(book);
        Optional<Book> loaded = bookRepository.findById(saved.getId());

        assertTrue(loaded.isPresent());
        assertNotNull(saved.getId());
        assertEquals("Integration Testing with Spring", loaded.get().getName());
        assertEquals("Jane Doe", loaded.get().getAuthor());
        assertEquals("9781234567890", loaded.get().getIsbn());
        assertEquals(BookFormats.Hardcover, loaded.get().getFormat());
        assertTrue(loaded.get().isBorrowed());
        assertFalse(loaded.get().isReference());
        assertTrue(loaded.get().isPremium());
    }

    @Test
    void save_whenDuplicateIsbn_throwsDataIntegrityViolationException() {
        Book first = new Book();
        first.setName("First");
        first.setAuthor("Author One");
        first.setIsbn("9781111111111");
        first.setFormat(BookFormats.Paperback);
        bookRepository.save(first);

        Book duplicate = new Book();
        duplicate.setName("Second");
        duplicate.setAuthor("Author Two");
        duplicate.setIsbn("9781111111111");
        duplicate.setFormat(BookFormats.EBook);

        assertThrows(DataIntegrityViolationException.class, () -> {
            bookRepository.save(duplicate);
            entityManager.flush();
        });
    }
}
