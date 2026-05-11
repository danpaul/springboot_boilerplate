package com.example.demo.service;

import com.example.demo.domain.policy.BorrowingPolicy;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Registers Mockito with JUnit 5 so @Mock/@InjectMocks fields are created before each test.
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    // Creates a Mockito test double instead of using a real repository implementation.
    @Mock
    private BookRepository bookRepository;

    // Another mock dependency controlled entirely from the test.
    @Mock
    private UserRepository userRepository;

    // Mocked policy lets us verify calls and force policy-related failures when needed.
    @Mock
    private BorrowingPolicy borrowingPolicy;

    // Builds BookService and injects all @Mock fields into its constructor automatically.
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
        // Stubbing: when this mock method is called, return predefined data.
        when(bookRepository.findAll()).thenReturn(books);

        Iterable<Book> result = bookService.findAll();

        assertEquals(books, result);
        // Verification: assert that collaboration with the mock happened as expected.
        verify(bookRepository).findAll();
    }

    @Test
    void findById_returnsOptionalBook() {
        // Stubbing a specific ID lookup on the repository mock.
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        // Verify the service delegated exactly this lookup to the mock.
        verify(bookRepository).findById(1);
    }

    @Test
    void save_delegatesToRepository() {
        // Stub save(...) so the mock returns the same entity.
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.save(book);

        assertEquals(book, result);
        // Verify save(...) was called on the dependency.
        verify(bookRepository).save(book);
    }

    @Test
    void update_delegatesToRepository() {
        // Stub update path (same repository method in this service).
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.update(book);

        assertEquals(book, result);
        // Verify update delegates to repository.save(...).
        verify(bookRepository).save(book);
    }

    @Test
    void delete_callsDeleteById() {
        bookService.delete(1);
        // Verify delete call was forwarded to the repository mock.
        verify(bookRepository).deleteById(1);
    }

    @Test
    void borrowBook_happyPath_updatesAndSavesUserAndBook() {
        // Stubbing repository lookups to simulate existing entities.
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        // Separate stub for user lookup on another mock.
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        // Stub book save result so the service returns a predictable object.
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.borrowBook(1, 10L);

        assertTrue(result.isBorrowed());
        assertTrue(user.getBorrowedBooks().contains(book));
        // Verify the policy mock was consulted before persisting changes.
        verify(borrowingPolicy).enforceBorrowingPolicy(user, book);
        // Verify user state is persisted.
        verify(userRepository).save(user);
        // Verify book state is persisted.
        verify(bookRepository).save(book);

        // InOrder verification checks sequence, not just whether calls occurred.
        InOrder inOrder = inOrder(userRepository, bookRepository);
        // First expected ordered call.
        inOrder.verify(userRepository).save(user);
        // Second expected ordered call.
        inOrder.verify(bookRepository).save(book);
    }

    @Test
    void borrowBook_throwsWhenBookNotFound() {
        // Stub missing book to drive the exception branch.
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.borrowBook(1, 10L));

        // never() + matcher (anyLong) verifies a method was not called with any long value.
        verify(userRepository, never()).findById(anyLong());
        // any() matcher means "any object of the expected parameter type".
        verify(borrowingPolicy, never()).enforceBorrowingPolicy(any(), any());
    }

    @Test
    void borrowBook_throwsWhenUserNotFound() {
        // Book exists in this scenario.
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        // User lookup fails, which should stop the flow.
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.borrowBook(1, 10L));

        // Verify policy is never reached when user is missing.
        verify(borrowingPolicy, never()).enforceBorrowingPolicy(any(), any());
    }

    @Test
    void borrowBook_throwsWhenBookAlreadyBorrowed() {
        book.setBorrowed(true);
        // Stub dependencies up to the policy call.
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        // doThrow(...).when(mock) is the preferred style for forcing exceptions on void methods.
        doThrow(new IllegalStateException("Book is already borrowed"))
                .when(borrowingPolicy).enforceBorrowingPolicy(user, book);

        assertThrows(IllegalStateException.class, () -> bookService.borrowBook(1, 10L));

        // Verify policy enforcement was attempted.
        verify(borrowingPolicy).enforceBorrowingPolicy(user, book);
        // Verify no save to user repository after policy failure.
        verify(userRepository, never()).save(any());
        // Verify no save to book repository after policy failure.
        verify(bookRepository, never()).save(any());
    }

    @Test
    void borrowBook_propagatesPolicyException() {
        // Stub lookup calls so execution reaches policy enforcement.
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        // Force policy to throw so we can test propagation.
        doThrow(new IllegalStateException("Policy rejected"))
                .when(borrowingPolicy).enforceBorrowingPolicy(user, book);

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> bookService.borrowBook(1, 10L));

        assertEquals("Policy rejected", exception.getMessage());
        // Verify no persistence side effects after exception.
        verify(userRepository, never()).save(any());
        // Verify no persistence side effects after exception.
        verify(bookRepository, never()).save(any());
    }
}
