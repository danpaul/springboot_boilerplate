package com.example.demo.mapper;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface BookMapper {
    BookResponseDto toResponseDto(Book book);

    Iterable<BookResponseDto> toResponseDto(Iterable<Book> books);

    Book toEntity(BookRequestDto bookRequestDto);
}

/**
 * Teaching example only: this shows roughly what MapStruct generates for BookMapper.
 */
class BookMapperManualExample {

    private final ReviewMapper reviewMapper;

    BookMapperManualExample(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    BookResponseDto toResponseDto(Book book) {
        if (book == null) {
            return null;
        }

        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setFormat(book.getFormat());

        if (book.getReviews() != null) {
            dto.setReviews(toList(reviewMapper.toResponseDto(book.getReviews())));
        }

        return dto;
    }

    Iterable<BookResponseDto> toResponseDto(Iterable<Book> books) {
        if (books == null) {
            return null;
        }

        List<BookResponseDto> result = new ArrayList<>();
        for (Book book : books) {
            result.add(toResponseDto(book));
        }
        return result;
    }

    Book toEntity(BookRequestDto bookRequestDto) {
        if (bookRequestDto == null) {
            return null;
        }

        Book book = new Book();
        book.setName(bookRequestDto.getName());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setFormat(bookRequestDto.getFormat());
        return book;
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }
}
