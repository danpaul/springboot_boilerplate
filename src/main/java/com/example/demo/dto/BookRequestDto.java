package com.example.demo.dto;

import com.example.demo.enums.BookFormats;
import lombok.Data;

import java.util.Optional;

// use Lombok @Data annotation to require all args in constructor
// create getters/setters and provide ToString method
@Data
public class BookRequestDto {
    private Optional<Integer> id;
    private String name;
    private String author;
    private String isbn;
    private BookFormats format;
}
