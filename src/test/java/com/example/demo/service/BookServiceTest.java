package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1);
        book.setBorrowed(false);

        user = new User();
        user.setId(10L);
        user.setMember(true);
        user.setPremiumMember(false);
        user.setBorrowedBooks(new ArrayList<>());
    }

    @Test
    void findAll_returnsAllBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        Iterable<Book> result = bookService.findAll();

        assertEquals(books, result);
        verify(bookRepository).findAll();
    }

    @Test
    void findById_returnsOptionalBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(bookRepository).findById(1);
    }

    @Test
    void save_delegatesToRepository() {
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.save(book);

        assertEquals(book, result);
        verify(bookRepository).save(book);
    }

    @Test
    void update_delegatesToRepository() {
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.update(book);

        assertEquals(book, result);
        verify(bookRepository).save(book);
    }

    @Test
    void delete_callsDeleteById() {
        bookService.delete(1);
        verify(bookRepository).deleteById(1);
    }

    @Test
    void borrowBook_happyPath_updatesAndSavesUserAndBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.borrowBook(1, 10L);

        assertTrue(result.isBorrowed());
        assertTrue(user.getBorrowedBooks().contains(book));
        verify(userRepository).save(user);
        verify(bookRepository).save(book);

        InOrder inOrder = inOrder(userRepository, bookRepository);
        inOrder.verify(userRepository).save(user);
        inOrder.verify(bookRepository).save(book);
    }

    @Test
    void borrowBook_throwsWhenBookNotFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.borrowBook(1, 10L));

        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    void borrowBook_throwsWhenUserNotFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.borrowBook(1, 10L));
    }

    @Test
    void borrowBook_throwsWhenBookAlreadyBorrowed() {
        book.setBorrowed(true);
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> bookService.borrowBook(1, 10L));

        assertEquals("Book is already borrowed", exception.getMessage());
        verify(userRepository, never()).save(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void borrowBook_propagatesPolicyException() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        user.setMember(false);

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> bookService.borrowBook(1, 10L));

        assertEquals("Only members can borrow books", exception.getMessage());
        verify(userRepository, never()).save(any());
        verify(bookRepository, never()).save(any());
    }
}
