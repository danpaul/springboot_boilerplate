package com.example.demo.mapper;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface BookMapper {
    BookResponseDto toResponseDto(Book book);

    Iterable<BookResponseDto> toResponseDto(Iterable<Book> books);

    @Mapping(target = "id", source = "id", qualifiedByName = "optionalIdToInt")
    Book toEntity(BookRequestDto bookRequestDto);

    @Named("optionalIdToInt")
    default int optionalIdToInt(Optional<Integer> id) {
        return id != null && id.isPresent() ? id.get() : 0;
    }
}
