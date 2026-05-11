package com.example.demo.mapper;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import org.mapstruct.Mapper;

/**
 * MapStruct marker for this mapper interface.
 *
 * <p>When you compile the project, MapStruct generates an implementation class (for example,
 * {@code BookMapperImpl}) so you do not write mapping boilerplate by hand.
 *
 * <p>MapStruct treats each interface method as a mapping contract: the parameter type is the
 * source, and the return type is the target. It then generates concrete method bodies that copy
 * matching fields (same name/type) and perform basic null checks.
 *
 * <p>For collection methods (for example {@code Iterable<Book>} to
 * {@code Iterable<BookResponseDto>}), MapStruct generates a loop and reuses the single-item
 * mapping method for each element.
 *
 * <p>{@code componentModel = "spring"} tells MapStruct to generate the class as a Spring bean,
 * so it can be injected with {@code @Autowired} or constructor injection.
 *
 * <p>This mapper only handles book fields and does not include related review mapping.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponseDto toResponseDto(Book book);

    Iterable<BookResponseDto> toResponseDto(Iterable<Book> books);

    Book toEntity(BookRequestDto bookRequestDto);
}
