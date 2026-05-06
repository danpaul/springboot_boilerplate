package com.example.demo.controller;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.service.BookService;
// Jakarta Validation API
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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
    Iterable<BookResponseDto> getAll() {
        return this.bookMapper.toResponseDto(this.bookService.findAll());
    }

    @GetMapping("/{id}")
    BookResponseDto get(@PathVariable int id) {
        Optional<Book> book = this.bookService.findById(id);
        if (book.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        return this.bookMapper.toResponseDto(book.get());
    }

    // Example of how to use validation annotations
    @PostMapping("")
    Book create(@RequestBody @Valid BookRequestDto bookRequestDto) {
        return this.bookService.save(this.bookMapper.toEntity(bookRequestDto));
    }

    @PutMapping("/{id}")
    Book update(@RequestBody BookRequestDto bookRequestDto, @PathVariable int id) {
        bookRequestDto.setId(id);
        return this.bookService.update(this.bookMapper.toEntity(bookRequestDto));
    }

    @DeleteMapping("/{id}")
    Object delete(@PathVariable int id) {
        this.bookService.delete(id);
        return new Object();
    }
}
