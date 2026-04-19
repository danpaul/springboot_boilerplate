package com.example.demo.controller;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Books", description = "Operations for managing books")
@RestController
@RequestMapping("books")
public class BookController {
    final private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    @Operation(summary = "Get all books", description = "Returns all books as a list of response DTOs.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Books retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BookResponseDto.class))
                    )
            )
    })
    public Iterable<BookResponseDto> getAll() {
        return BookMapper.toResponseDto(this.bookService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Returns a single book for the given id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            )
    })
    public BookResponseDto get(@PathVariable int id) {
        Optional<Book> book = this.bookService.findById(id);
        // TODO: handle 404
        if (book.isEmpty()) return new BookResponseDto();
        return BookMapper.toResponseDto(book.get());
    }

    @PostMapping("")
    @Operation(summary = "Create a book", description = "Creates a new book from the provided request payload.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
            )
    })
    public Book create(
            @RequestBody(description = "Book payload used to create a new book", required = true)
            @org.springframework.web.bind.annotation.RequestBody BookRequestDto bookRequestDto
    ) {
        return this.bookService.save(BookMapper.toEntity(bookRequestDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Updates an existing book for the given id using the provided payload.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            )
    })
    public Book update(
            @RequestBody(description = "Book payload used to update an existing book", required = true)
            @org.springframework.web.bind.annotation.RequestBody BookRequestDto bookRequestDto,
            @PathVariable int id
    ) {
        return this.bookService.update(BookMapper.toEntity(bookRequestDto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Deletes the book for the given id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book deleted successfully",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            )
    })
    public Object delete(@PathVariable int id) {
        this.bookService.delete(id);
        return new Object();
    }

}
