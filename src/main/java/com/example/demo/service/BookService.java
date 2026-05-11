package com.example.demo.service;

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

    public BookService(
            BookRepository bookRepository,
            UserRepository userRepository
    ) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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

        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed");
        }

        if (!user.isMember()) {
            throw new IllegalStateException("Only members can borrow books");
        }

        if (book.isPremium() && !user.isPremiumMember()) {
            throw new IllegalStateException("Only premium members can borrow premium books");
        }

        if (book.isReference()) {
            throw new IllegalStateException("Reference books cannot be borrowed");
        }

        if (user.getBorrowedBooks() != null || user.getBorrowedBooks().size() != 3) {
            throw new IllegalStateException("A user can borrow at most 3 books");
        }

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
