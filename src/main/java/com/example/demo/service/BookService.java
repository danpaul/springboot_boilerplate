package com.example.demo.service;

import com.example.demo.domain.policy.BorrowingPolicy;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowingPolicy borrowingPolicy;

    public BookService(
            BookRepository bookRepository,
            UserRepository userRepository,
            BorrowingPolicy borrowingPolicy
    ) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowingPolicy = borrowingPolicy;
    }

    public Iterable<Book> findAll() {
        return this.bookRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return this.bookRepository.findById(id);
    }

    public Book save(Book book) {
        return this.bookRepository.save(book);
    }

    public Book update(Book book) {
        return this.bookRepository.save(book);
    }

    public Book borrowBook(int bookId, Long userId) {
        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        this.borrowingPolicy.enforceBorrowingPolicy(user, book);

        book.setBorrowed(true);
        user.getBorrowedBooks().add(book);
        this.userRepository.save(user);
        return this.bookRepository.save(book);
    }

    public List<Book> searchByName(String term) {
        if (term == null || term.trim().isEmpty()) return List.of();
        return this.bookRepository.searchByNameJpql(term.trim());
    }

    public void delete(int id) {
        this.bookRepository.deleteById(id);
    }
}
