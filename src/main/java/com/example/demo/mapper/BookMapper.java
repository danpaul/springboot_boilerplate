package com.example.demo.mapper;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {
    // maps entity to DTO
    public static BookResponseDto toResponseDto(Book book) {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(book.getId());
        bookResponseDto.setName(book.getName());
        bookResponseDto.setAuthor(book.getAuthor());
        bookResponseDto.setIsbn(book.getIsbn());
        bookResponseDto.setFormat(book.getFormat());
        return bookResponseDto;
    }

    // maps entities to DTOs
    public static Iterable<BookResponseDto> toResponseDto(Iterable<Book> books) {
        List<BookResponseDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            bookDtos.add(toResponseDto(book));
        }
        return bookDtos;
    }

    // maps request DTO to entity
    public static Book toEntity(BookRequestDto bookRequestDto) {
        Book book = new Book();
        if (bookRequestDto.getId() != null) {
            book.setId(bookRequestDto.getId().orElseThrow());
        }
        book.setName(bookRequestDto.getName());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setFormat(bookRequestDto.getFormat());
        book.setIsbn(bookRequestDto.getIsbn());
        return book;
    }
}
