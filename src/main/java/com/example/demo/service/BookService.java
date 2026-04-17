package com.example.demo.service;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.entity.Book;
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

    public Book save(BookRequestDto bookRequestDto) {
        Book book = new Book();
        // TODO: move to mapper
        book.setName(bookRequestDto.getName());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setFormat(bookRequestDto.getFormat());
        return this.bookRepository.save(book);
    }

    public Book update(BookRequestDto bookRequestDto, int id) {
        Book book = this.bookRepository.findById(id).orElseThrow();

        // TODO: move to mapper
        if(bookRequestDto.getName() != null) {
            book.setName(bookRequestDto.getName());
        }
        if(bookRequestDto.getIsbn() != null) {
            book.setIsbn(bookRequestDto.getIsbn());
        }
        if(bookRequestDto.getFormat() != null) {
            book.setFormat(bookRequestDto.getFormat());
        }
        if(bookRequestDto.getAuthor() != null) {
            book.setAuthor(bookRequestDto.getAuthor());
        }
        return this.bookRepository.save(book);
    }

    public void delete(int id) {
        this.bookRepository.deleteById(id);
    }
}
