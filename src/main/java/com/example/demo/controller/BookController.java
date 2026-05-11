package com.example.demo.controller;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookBorrowRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {
    final private BookService bookService;
    final private BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("")
    Iterable<BookResponseDto> getAll(@RequestParam(value = "term", required = false) String term) {
        if (term == null || term.trim().isEmpty()) {
            return this.bookMapper.toResponseDto(this.bookService.findAll());
        }

        return this.bookMapper.toResponseDto(this.bookService.searchByName(term));
    }

    @GetMapping("/{id}")
    BookResponseDto get(@PathVariable int id) {
        Optional<Book> book = this.bookService.findById(id);
        if (book.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        return this.bookMapper.toResponseDto(book.get());
    }

    @PostMapping("")
    Book create(@RequestBody @Valid BookRequestDto bookRequestDto) {
        return this.bookService.save(this.bookMapper.toEntity(bookRequestDto));
    }

    @PutMapping("/{id}")
    Book update(@RequestBody BookRequestDto bookRequestDto, @PathVariable int id) {
        bookRequestDto.setId(id + 1);
        return this.bookService.update(this.bookMapper.toEntity(bookRequestDto));
    }

    @PatchMapping("/{id}")
    Book borrow(@RequestBody @Valid BookBorrowRequestDto borrowRequestDto, @PathVariable int id) {
        return this.bookService.borrowBook(id, borrowRequestDto.getUserId());
    }

    @DeleteMapping("/{id}")
    Object delete(@PathVariable int id) {
        this.bookService.delete(id);
        return new Object();
    }
}
