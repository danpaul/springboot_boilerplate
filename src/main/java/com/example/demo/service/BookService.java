package com.example.demo.service;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.entity.Book;
import com.example.demo.enums.BookFormats;
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
        book.setName(bookRequestDto.name());
        book.setAuthor(bookRequestDto.author());
        book.setIsbn(bookRequestDto.isbn());
        book.setFormat(BookFormats.valueOf(bookRequestDto.format()));
        return this.bookRepository.save(book);
    }

    public Book update(BookRequestDto bookRequestDto, int id) {
        Book book = this.bookRepository.findById(id).orElseThrow();

        // TODO: move to mapper
        if(bookRequestDto.name() != null) {
            book.setName(bookRequestDto.name());
        }
        if(bookRequestDto.isbn() != null) {
            book.setIsbn(bookRequestDto.isbn());
        }
        if(bookRequestDto.format() != null) {
            book.setFormat(BookFormats.valueOf(bookRequestDto.format()));
        }
        if(bookRequestDto.author() != null) {
            book.setAuthor(bookRequestDto.author());
        }
        return this.bookRepository.save(book);
    }

    public void delete(int id) {
        this.bookRepository.deleteById(id);
    }
}
