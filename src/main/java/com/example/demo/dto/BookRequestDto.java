package com.example.demo.dto;

import com.example.demo.enums.BookFormats;
import lombok.Data;


// use Lombok @Data annotation to require all args in constructor
// create getters/setters and provide ToString method
@Data
public class BookRequestDto {
    private String name;
    private String author;
    private String isbn;
    private BookFormats format;
}
