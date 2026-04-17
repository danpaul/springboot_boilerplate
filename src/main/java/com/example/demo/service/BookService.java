package com.example.demo.service;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

    public void delete(int id) {
        this.bookRepository.deleteById(id);
    }
}
